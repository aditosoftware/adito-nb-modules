package de.adito.aditoweb.nbm.nbide.nbaditointerface.metrics;

/**
 * Measure the amount of processing the EDT Thread is currenltly doing, and if it busy more than a certain percent of time, log an exception
 *
 * @author m.kaspera, 20.12.2021
 */
public interface IEDTStressDetector
{

  /**
   * This is the key for the property that controls at which level (in percent) the "stress alarm" is triggered.
   * The property itself should be a double and give the amount in percent (e.g. 66.0 for 66%)
   */
  String ALERT_LEVEL = "de.adito.analytics.edt.stressfactor";

  /**
   * starts the EDTStressDetector, this should start its own Thread or Executor in which the processing load of the EDT is measured
   */
  void start();

}
