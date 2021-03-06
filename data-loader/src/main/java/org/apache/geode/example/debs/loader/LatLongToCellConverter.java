package org.apache.geode.example.debs.loader;

import org.apache.geode.example.debs.model.Cell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by sbawaskar on 11/29/15.
 */
public class LatLongToCellConverter {

  private final double latDelta = 0.004491556;
  private final double longDelta = 0.005986;
  private final double START_LATITUDE = 41.474937 + (latDelta/2);
  private final double START_LONGITUDE = -74.913585 - (longDelta/2);
  private final double ENDING_LATITUDE = START_LATITUDE - (latDelta * 300);
  private final double ENDING_LONGITUDE = START_LONGITUDE + (longDelta * 300);

  private static final Logger logger = LogManager.getLogger();

  public Cell getCell(double latitude, double longitude) {
    logger.debug(String.format("### Lat: %s, Long %s", latitude, longitude));

    //add 1 since the co-ordinate system starts at 1,1 not 0,0
    verifyCellLocation(latitude, longitude);
    int x = (int) ((START_LATITUDE - latitude) / latDelta) + 1;
    int y = (int) (Math.abs(START_LONGITUDE - longitude) / longDelta) + 1;

    return new Cell(x, y);
  }

  /**
   * verify that the cell lies between 1 and 300 for both lat and long
   */
  private void verifyCellLocation(double latitude, double longitude) {
    if (latitude > START_LATITUDE || latitude < ENDING_LATITUDE) {
      throw new IllegalArgumentException(String.format("Latitude %s is out of range", latitude));
    }
    if (longitude < START_LONGITUDE || longitude > ENDING_LONGITUDE) {
      throw new IllegalArgumentException(String.format("Longitude %s is out of range", longitude));
    }
  }
}