package uk.jixun.project.Gui;

import guru.nidi.graphviz.attribute.*;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.*;
import org.jetbrains.annotations.Nullable;
import uk.jixun.project.Helper.ArrayReduce;
import uk.jixun.project.Simulator.DispatchRecord.IDispatchRecord;
import uk.jixun.project.Simulator.ISmHistory;
import uk.jixun.project.SimulatorConfig.ISimulatorConfigFormValue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static guru.nidi.graphviz.model.Factory.*;

public class HistoryToDependencyGraph {
  private static Node getNode(IDispatchRecord record) {
    return node(nodeId(record));
  }

  private static String nodeId(IDispatchRecord record) {
    return String.format("node_%d", record.getExecutionId());
  }

  static private Graph getGraph(ISmHistory history, ISimulatorConfigFormValue config) {
    // TODO: Generate Image.
    int lastId = history.getLastRecord().getExecutionId();
    int lastCycleStart = history
      .getSortedHistoryBetween(0, lastId)
      .stream()
      .max(Comparator.comparing(IDispatchRecord::getInstEndCycle))
      .map(IDispatchRecord::getInstEndCycle)
      .orElse(0) + 1;
    HashMap<Integer, List<Node>> cycleMap = new HashMap<>();
    HashMap<Integer, Node> nodeMap = new HashMap<>();
    List<Node> cycleLine = new ArrayList<>(lastCycleStart);
    for(int i = 0; i < lastCycleStart; i++) {
      cycleLine.add(
        node("c_" + i)
          .with(Shape.NONE)
          .with(Label.of("" + i))
      );
    }

    List<IDispatchRecord> records = history
      .getSortedHistoryBetween(0, history.getLastRecord().getExecutionId());

    records
      .forEach(record -> {
        Node n1 = getNode(record)
          .with(Label.of(record.getExecutable().getOriginalLine()));
        nodeMap.put(record.getExecutionId(), n1);

        int cycle = record.getInstStartCycle();
        if (!cycleMap.containsKey(cycle)) {
          ArrayList<Node> l = new ArrayList<>();
          l.add(node("c_" + cycle));
          cycleMap.put(cycle, l);
        }
        cycleMap.get(cycle).add(n1);
      });

    List<Node> depNodes = records.stream().map((record) -> {
      List<IDispatchRecord> depRecord = record.getDependencies(true);
      if (depRecord == null || depRecord.size() == 0) {
        return null;
      }

      Node n1 = nodeMap.get(record.getExecutionId());

      Link[] links = depRecord
        .stream()
        .filter(r -> Math.abs(r.getInstEndCycle() - record.getInstEndCycle()) <= config.getDependencyThreshold())
        .map(dep -> to(nodeMap.get(dep.getExecutionId())))
        .toArray(Link[]::new);

      System.out.println("dep (" + record.getExecutionId() +  "):" + depRecord
        .stream()
        .map(dep -> String.valueOf(dep.getExecutionId()))
        .collect(Collectors.joining(" -> "))
      );

      return n1.link(links);
    }).filter(Objects::nonNull).collect(Collectors.toList());

    for (int i = 1; i < cycleLine.size(); i++) {
      depNodes.add(cycleLine.get(i - 1).link(to(cycleLine.get(i)).with(Style.INVIS)));
    }

    Graph g = graph("depGraph")
      .directed()
      .graphAttr().with(RankDir.TOP_TO_BOTTOM)
      .nodeAttr().with(Shape.RECTANGLE)
      .with(cycleLine)
      .with(depNodes.toArray(Node[]::new));


    // Same Rank
    g = ArrayReduce.reduce(
      cycleMap.values(),
      (gg, value, index) -> gg.with(graph().graphAttr().with(Rank.SAME).with(value)),
      g
    );

    return g;
  }

  static BufferedImage fromHistory(ISmHistory history, ISimulatorConfigFormValue config) {
    return Graphviz.fromGraph(getGraph(history, config)).render(Format.SVG).toImage();
  }

  static String graphToDot(ISmHistory history, ISimulatorConfigFormValue config) {
    Graph g = getGraph(history, config);
    return new Serializer((MutableGraph) g).serialize();
  }
}
