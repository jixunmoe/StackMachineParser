package uk.jixun.project.Helper;

import java.util.ArrayList;
import java.util.List;

public class GridData<T> {
  private List<List<T>> data = new ArrayList<>();
  private int largestColIndex = 0;

  private static <T> void ensureCellExist(List<T> data, int index) {
    if (index >= data.size()) {
      int toAdd = index - (data.size() - 1);

      while (toAdd-- > 0) {
        data.add(null);
      }
    }
  }

  public int getRows() {
    return data.size();
  }

  public int getCols() {
    return largestColIndex + 1;
  }

  public List<T> getRow(int row) {
    if (row < getRows()) {
      List<T> rowData = data.get(row);

      // normalise size
      ensureCellExist(rowData, largestColIndex);

      return new ArrayList<>(rowData);
    }

    return null;
  }

  public List<T> getCol(int col) {
    int rows = getRows();
    List<T> result = new ArrayList<>(rows);

    for (int i = 0; i < rows; i++) {
      result.add(get(i, col));
    }

    return result;
  }

  public void set(int row, int col, T item) {
    ensureCellExist(row, col);
    data.get(row).set(col, item);
  }

  public T get(int row, int col) {
    ensureCellExist(row, col);
    return data.get(row).get(col);
  }

  private void ensureCellExist(int row, int col) {
    largestColIndex = Math.max(col, largestColIndex);

    ensureCellExist(data, row);
    if (data.get(row) == null) {
      data.set(row, new ArrayList<>());
    }

    List<T> rowData = data.get(row);
    ensureCellExist(rowData, col);
  }
}
