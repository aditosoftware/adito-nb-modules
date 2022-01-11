package de.adito.aditoweb.nbm.nbide.nbaditointerface.metrics;

/**
 * @author m.kaspera, 05.01.2022
 */
public interface IRunnableDetector
{

  /**
   * starts the Detector, this should start its own Thread or Executor in which the metric is calculated and processed. If called more than once,
   * should not start up a second running instance but either cancel the first instance and start a new one or do nothing on the second invocation
   */
  void start();

}
