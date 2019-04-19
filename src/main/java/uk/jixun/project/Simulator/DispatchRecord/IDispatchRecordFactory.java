package uk.jixun.project.Simulator.DispatchRecord;

public interface IDispatchRecordFactory {
  IDependencyResolver getResolver(IDispatchRecord record);
}
