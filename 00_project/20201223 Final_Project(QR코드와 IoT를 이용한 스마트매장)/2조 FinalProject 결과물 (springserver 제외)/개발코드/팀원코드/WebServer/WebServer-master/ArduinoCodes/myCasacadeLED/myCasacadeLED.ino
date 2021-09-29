//We always have to include the library
#include "LedControl.h"
#define dotCount 4
/*
  Now we need a LedControl to work with.
 ***** These pin numbers will probably not work with your hardware *****
  pin 12 is connected to the DataIn
  pin 11 is connected to the CLK
  pin 10 is connected to LOAD
 ***** Please set the number of devices you have *****
  But the maximum default of 8 MAX72XX wil also work.
*/
LedControl lc = LedControl(A8, A9, A10, 4);

/* we always wait a bit between updates of the display */
unsigned long delaytime = 200;

/*
  This time we have more than one device.
  But all of them have to be initialized
  individually.
*/
void setup() {
  //we have to init all devices in a loop
  for (int address = 0; address < dotCount; address++) {
    /*The MAX72XX is in power-saving mode on startup*/
    lc.shutdown(address, false);
    /* Set the brightness to a medium values */
    lc.setIntensity(address, 8);
    /* and clear the display */
    lc.clearDisplay(address);
  }
}

void loop() {
  //read the number cascaded devices
  unsigned long currentMillis = millis();
  //we have to init all devices in a loop
  int address = 0;
  int col = 0;
  int row = 7;
  while (address < dotCount) {
    if (address != 3) {
      while (row >= 0) {
        lc.setLed(address, col, row, true);
        delay(delaytime);
        row --;
      } // on the first column
      row = 7;
      // until 3rd device
    } else {
      // at 4th device
      while (row  > 2) {
        lc.setLed(address, col, row, true);
        delay(delaytime);
        row --;
      } // vertical LED on
      while (col < 7) {
        lc.setLed(address, col, row, true);
        delay(delaytime);
        col ++;
      } // go horizontal direction
      int count = 0;
      while (col > 4) {
        if (col == 7) {
          lc.setLed(address, col, row, true);
        } else if (col < 7) {
          lc.setLed(address, col, row + count, true);
          lc.setLed(address, col, row - count, true);
        }
        count++;
        delay(delaytime);
        col --;
      }
    }
    address++;
  }

} //loop end

void dotMatrixOnOff(bool on, const int interval) {

}

