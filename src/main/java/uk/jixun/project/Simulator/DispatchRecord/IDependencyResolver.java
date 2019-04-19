package uk.jixun.project.Simulator.DispatchRecord;

import java.util.List;

public interface IDependencyResolver {
  boolean allResolved();
  boolean resolveDependency(IDispatchRecord record);
  List<IDispatchRecord> getDependencies();
}
