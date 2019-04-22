package uk.jixun.project.Gui;

import uk.jixun.project.Helper.LazyCache;
import uk.jixun.project.Helper.LazyCacheResolver;
import uk.jixun.project.Simulator.ISmHistory;
import uk.jixun.project.SimulatorConfig.ISimulatorConfigFormValue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

public class GraphCanvas extends JComponent implements IGraphCanvas {
  private Thread worker = null;

  private enum Status {
    Crashed,
    ConfigChanged,
    NotLoaded,
    Loaded,
    Loading,
  }

  final private AtomicBoolean haveNewWork = new AtomicBoolean(false);

  private Status status = Status.NotLoaded;
  private ISmHistory history = null;
  private LazyCache<BufferedImage> image = new LazyCache<>(this::generateImage);
  private ISimulatorConfigFormValue config = null;

  public GraphCanvas() {
    setFont(GuiManager.getFontMono());
    setupDrag();
  }

  private void generateImage(LazyCacheResolver<BufferedImage> resolver) {
    if (history == null) {
      setStatus(Status.NotLoaded);
      resolver.reject();
      return;
    }

    setStatus(Status.Loading);
    BufferedImage result;
    try {
      try (final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./graph.dot"), StandardCharsets.UTF_8))) {
        bw.write(HistoryToDependencyGraph.graphToDot(history, config));
      }

      result = HistoryToDependencyGraph.fromHistory(history, config);
    } catch (Exception|Error ex) {
      // Negative Image Size Exception, Out-of-Memory Error
      // Image is too large
      // TODO: Allow user to download the image without
      resolver.reject();
      setPreferredSize(new Dimension(200, 200));
      ex.printStackTrace();
      setStatus(Status.Crashed);
      return;
    }
    resolver.resolve(result);
  }

  private void drawMessage(Graphics g, String text) {
    g.setColor(Color.BLACK);
    int lineHeight = getFontMetrics(getFont()).getHeight();

    int y = 10 + lineHeight;
    for (String line : text.split("\n")) {
      g.drawString(line, 10, y);
      y += lineHeight;
    }
  }

  @Override
  protected void paintChildren(Graphics g) {
    super.paintChildren(g);

    g.setFont(getFont());

    // Clear drawable area.
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, getWidth(), getHeight());

    switch (status) {
      case Crashed:
        drawMessage(g, "Graph Generation failed.\n >> Try with a smaller threshold?");
        break;
      case ConfigChanged:
        drawMessage(g, "Configuration changed!\n >> Hit 'Run' to restart & record simulation?");
        break;
      case NotLoaded:
        drawMessage(g, "No execution history loaded. \n >> 'Load' and 'Run' to record some data?");
        break;
      case Loading:
        drawMessage(g, "Generating graph, please wait...");
        break;
      case Loaded:
        drawGraph(g);
        break;
    }
  }

  private void drawGraph(Graphics g) {
    // Lazy draw - don't bother with the area invisible.
    int showNav = 1;
    int colWidth = 100;

    JViewport viewPort = getViewPort();
    Rectangle view = viewPort.getViewRect();
    view.width += view.x;
    view.height += view.y;
    g.drawImage(image.get(),
      // int dstx1, int dsty1, int dstx2, int dsty2,
      view.x, view.y, view.width, view.height,
      view.x, view.y, view.width, view.height,
      null);

    // Fix cycle to the left.
    if (view.x > showNav) {
      int xEnd = view.x + colWidth;

      g.drawImage(image.get(),
        // int dstx1, int dsty1, int dstx2, int dsty2,
        view.x, view.y, xEnd, view.height,
        0, view.y, colWidth, view.height,
        null);
      g.setColor(Color.BLACK);
      g.drawLine(xEnd, view.y, xEnd, view.height);
    }
  }

  private JViewport getViewPort() {
    return (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, this);
  }

  public void setConfig(ISimulatorConfigFormValue config) {
    if (this.config == null) {
      this.config = config;
      return;
    }

    boolean configChanged =
      this.config.getAluCount() != config.getAluCount()
        || this.config.getMemoryPortCount() != config.getMemoryPortCount()
        || this.config.getSearchDepth() != config.getSearchDepth();
    boolean needReDraw = config.getDependencyThreshold() != this.config.getDependencyThreshold();

    this.config = config;
    if (configChanged) {
      setStatus(Status.ConfigChanged);
    } else if (needReDraw && status != Status.ConfigChanged) {
      prepareRepaint();
    }
  }

  @Override
  public void setHistory(ISmHistory history) {
    this.history = history;
    if (status == Status.ConfigChanged) {
      setStatus(Status.Loading);
    } else {
      prepareRepaint();
    }
  }

  private void prepareRepaint() {
    image.invalidate();

    synchronized (haveNewWork) {
      if (!haveNewWork.getAndSet(true)) {
        if (worker == null || !worker.isAlive()) {
          worker = new Thread(this::doWork);
          worker.start();
        }
      }
    }
  }

  private void setupDrag() {
    MouseAdapter ma = new MouseAdapter() {
      private Point origin = null;

      @Override
      public void mousePressed(MouseEvent e) {
        origin = new Point(e.getPoint());
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        origin = null;
      }

      @Override
      public void mouseDragged(MouseEvent e) {
        if (origin != null) {
          JViewport viewPort = getViewPort();
          if (viewPort != null) {
            int deltaX = origin.x - e.getX();
            int deltaY = origin.y - e.getY();

            Rectangle view = viewPort.getViewRect();
            view.x += deltaX;
            view.y += deltaY;

            GraphCanvas.this.scrollRectToVisible(view);
          }
        }
      }

    };

    this.addMouseListener(ma);
    this.addMouseMotionListener(ma);

    setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
  }

  private void setStatus(Status status) {
    this.status = status;
    repaint();
  }

  private void doWork() {
    // Update status.
    setStatus(Status.Loading);

    this.scrollRectToVisible(new Rectangle(0, 0, 0, 0));

    synchronized (haveNewWork) {
      haveNewWork.set(false);
    }

    BufferedImage result = image.get();
    if (image.isCached()) {
      setPreferredSize(new Dimension(result.getWidth(), result.getHeight()));
      setSize(result.getWidth(), result.getHeight());
      setStatus(Status.Loaded);
    }
    System.out.println("work complete");

    boolean gotNewWork;
    synchronized (haveNewWork) {
      gotNewWork = haveNewWork.getAndSet(false);
    }

    if (gotNewWork) {
      doWork();
    }
  }
}
