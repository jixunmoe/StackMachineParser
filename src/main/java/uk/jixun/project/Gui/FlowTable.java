package uk.jixun.project.Gui;

import org.apache.commons.text.StringEscapeUtils;
import uk.jixun.project.Helper.HashMapBuilder;
import uk.jixun.project.Simulator.DispatchRecord.IDispatchRecord;
import uk.jixun.project.Simulator.ISmHistory;

import java.util.*;
import java.util.stream.Collectors;

public class FlowTable {
  private ISmHistory history;

  private String h(String tag, Map<String, String> attrs, List<String> html) {
    return h(tag, attrs, String.join("", html));
  }

  private String escape(String text) {
    return StringEscapeUtils.escapeHtml4(text);
  }

  private String h(String tag, Map<String, String> attrs, String... html) {
    StringBuffer sb = new StringBuffer();
    sb.append("<");
    sb.append(tag);

    if (attrs != null && attrs.keySet().size() > 0) {
      attrs.forEach((key, val) -> {
        sb.append(" ");
        sb.append(escape(key));
        sb.append("=\"");
        sb.append(escape(val));
        sb.append("\"");
      });
    }

    sb.append(">");

    sb.append(String.join("", html));

    sb.append("</");
    sb.append(tag);
    sb.append(">");

    return sb.toString();
  }

  private String h(String tag, Map<String, String> attrs) {
    return h(tag, attrs, "");
  }

  private String h(String tag, String... html) {
    return h(tag, null, html);
  }

  private String h(String tag) {
    return h(tag, (Map<String, String>) null, "");
  }

  private String buildFromHistory(ISmHistory history) {
    List<IDispatchRecord> records = history.getSortedHistoryBetween(0, history.getLastRecord().getExecutionId());

    int rows = records
      .stream()
      .max(Comparator.comparing(IDispatchRecord::getInstEndCycle))
      .map(IDispatchRecord::getInstEndCycle)
      .orElse(0) + 1;

    final List<StringBuilder> table = new ArrayList<>(rows);
    final List<Integer> nWidth = new ArrayList<>(rows);
    for (int i = 0; i < rows; i++) {
      StringBuilder sb = new StringBuilder();
      sb.append(h("th", String.valueOf(i)));
      table.add(sb);

      int cycle = i;
      nWidth.add((int) history.filter(r -> r.executesAt(cycle)).count());
    }

    int maxWidth = Math.max(Collections.max(nWidth), 1);

    records.forEach(record -> {
      table.get(record.getInstStartCycle()).append(
        h(
          "td",
          record.getCycleLength() == 0 ? null :
            HashMapBuilder.<String, String>create()
              .set("rowspan", String.valueOf(record.getCycleLength())),
          escape(record.getExecutable().getOriginalLine())
        )
      );
    });

    for (int i = 0; i < rows; i++) {
      int cell = nWidth.get(i);
      int pad = maxWidth - cell;

      if (pad > 0) {
        table.get(i).append(
          h(
            "td",
            HashMapBuilder.<String, String>create()
              .set("colspan", String.valueOf(pad)),
            ""
          )
        );
      }
    }


    return h(
      "html",
      h(
        "body",
        h("style", "table { border-collapse: collapse } td,th { border: 1px solid #ccc }"),
        h(
          "table",
          h(
            "tr",
            h("th", "Cycle"),
            h(
              "th",
              HashMapBuilder.<String, String>create()
                .set("colspan", String.valueOf(maxWidth)),
              "Instructions")
          ),
          table.stream().map(
            row -> h("tr", row.toString())
          ).collect(Collectors.joining())
        )
      )
    );
  }

  public static String fromHistory(ISmHistory history) {
    if (history == null) {
      return "History not loaded.";
    }

    FlowTable builder = new FlowTable();
    String html = builder.buildFromHistory(history);
    System.out.println(html);
    return html;
  }
}
