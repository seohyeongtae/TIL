//We always have to include the library
#include "LedControl.h"
#define dotCount 4
#define dotInterval 50
/*
  Now we need a LedControl to work with.
 ***** These pin numbers will probably not work with your hardware *****
  pin 12 is connected to the DataIn
  pin 11 is connected to the CLK
  pin 10 is connected to LOAD
 ***** Please set the number of devices you have *****
  But the maximum default of 8 MAX72XX wil also work.
*/
// DIN , CLK, CS
LedControl lc = LedControl(A8, A9, A10, 4);
LedControl lc1 = LedControl(1, 2, 3, 4);


/*
  This time we have more than one device.
  But all of them have to be initialized
  individually.
*/
void setup() {
  initDotMatrix();
}
int dotAddressLeft = 0;
int dotColLeft = 0;
int dotRowLeft = 7;
int arrowHeadStepLeft = 0;
unsigned long dotActMillisLeft = 0;
bool reachedTipEndLeft = false;
bool onOffDotMatrixLeft = true;
int leftDotMatrixCycle = 0;

int dotAddressRight = 0;
int dotColRight = 7;
int dotRowRight = 7;
int arrowHeadStepRight = 0;
unsigned long dotActMillisRight = 0;
bool reachedTipEndRight = false;
bool onOffDotMatrixRight = true;
int rightDotMatrixCycle = 0;

void loop() {
  //read the number cascaded devices
  unsigned long currentMillis = millis();
  if (leftDotMatrixCycle < 6) {
    if (leftDotMatrixCycle % 2 == 0) {
      dotMatrixOnOffLeft(true, currentMillis);
    } else {
      dotMatrixOnOffLeft(false, currentMillis);
    }
  }
  if (rightDotMatrixCycle < 6) {
    if (rightDotMatrixCycle % 2 == 0) {
      dotMatrixOnOffRight(true, currentMillis);
    } else {
      dotMatrixOnOffRight(false, currentMillis);
    }
  }
} // main loop ends

void initDotMatrix() {
  for (int dotAddressLeft = 0; dotAddressLeft < dotCount; dotAddressLeft++) {
    /*The MAX72XX is in power-saving mode on startup*/
    lc.shutdown(dotAddressLeft, false);
    /* Set the brightness to a medium values */
    lc.setIntensity(dotAddressLeft, 8);
    /* and clear the display */
    lc.clearDisplay(dotAddressLeft);
    lc1.shutdown(dotAddressLeft, false);
    lc1.setIntensity(dotAddressLeft, 8);
    lc1.clearDisplay(dotAddressLeft);
  }
}

void dotMatrixOnOffLeft(bool modeOnOff, unsigned long currentMillis) {
  //Run only switch for DotMatrix is on.
  if (onOffDotMatrixLeft) {
    //Check interval.
    if (currentMillis - dotActMillisLeft >= dotInterval ) {
      //record last activated time
      dotActMillisLeft = currentMillis;

      // Run Only when it is needed
      if (dotAddressLeft < dotCount) {
        // on #1~3
        if (dotAddressLeft != dotCount - 1) {
          if (dotRowLeft >= 0) {
            // turn led one by one in vertical direction
            lc.setLed(dotAddressLeft, dotColLeft, dotRowLeft, modeOnOff);
            dotRowLeft --;
            if (dotRowLeft == -1) {
              //move on to next matrix
              dotAddressLeft++;
              // Initialize the number
              dotRowLeft = 7;
            }
          }
        } else { //something to do on last matrix
          //before drawing arrow head
          if (!reachedTipEndLeft) {
            //draw vertical
            if (dotRowLeft > 2) {
              lc.setLed(dotAddressLeft, dotColLeft, dotRowLeft, modeOnOff);
              dotRowLeft--;
            } else {
              //draw horizontal
              if (dotColLeft < 7) {
                lc.setLed(dotAddressLeft, dotColLeft, dotRowLeft, modeOnOff);
                dotColLeft ++;
              } else if (dotColLeft == 7) {
                // reached the end of L shape
                lc.setLed(dotAddressLeft, dotColLeft, dotRowLeft, modeOnOff);
                reachedTipEndLeft = true;
              }
            } // horizontal draw finished
          } else {
            //draw arrow head
            // draw head.
            arrowHeadStepLeft++;
            dotColLeft--;
            lc.setLed(dotAddressLeft, dotColLeft, dotRowLeft + arrowHeadStepLeft, modeOnOff);
            lc.setLed(dotAddressLeft, dotColLeft, dotRowLeft - arrowHeadStepLeft, modeOnOff);
            if (arrowHeadStepLeft == 2) {

              leftDotMatrixCycle++;
              dotAddressLeft = 0;
              dotColLeft = 0;
              dotRowLeft = 7;
              arrowHeadStepLeft = 0;
              reachedTipEndLeft = false;
              onOffDotMatrixLeft = true;
            }
          }// draw head finished
        } // last matrix finished
      }
    }//interval finished
  } // onOffDotMatrixLeft Finished
}

void dotMatrixOnOffRight(bool modeOnOff, unsigned long currentMillis) {
  //Run only switch for DotMatrix is on.
  if (onOffDotMatrixLeft) {
    //Check interval.
    if (currentMillis - dotActMillisRight >= dotInterval ) {
      //record last activated time
      dotActMillisRight = currentMillis;

      // Run Only when it is needed
      if (dotAddressRight < dotCount) {
        // on #1~3
        if (dotAddressRight != dotCount - 1) {
          if (dotRowRight >= 0) {
            // turn led one by one in vertical direction
            lc1.setLed(dotAddressRight, dotColRight, dotRowRight, modeOnOff);
            dotRowRight --;
            if (dotRowRight == -1) {
              //move on to next matrix
              dotAddressRight++;
              // Initialize the number
              dotRowRight = 7;
            }
          }
        } else { //something to do on last matrix
          //Draw horiozontal on last matrix
          if (!reachedTipEndRight) {
            //draw vertical
            if (dotRowRight > 2) {
              lc1.setLed(dotAddressRight, dotColRight, dotRowRight, modeOnOff);
              dotRowRight--;
            } else {
              //draw horizontal
              if (dotColRight > 0) {
                lc1.setLed(dotAddressRight, dotColRight, dotRowRight, modeOnOff);
                dotColRight--;
              } else if (dotColRight == 0) {
                // reached the end of L shape
                lc1.setLed(dotAddressRight, dotColRight, dotRowRight, modeOnOff);
                reachedTipEndRight = true;
              }
            } // horizontal draw finished
          } else {
            //draw arrow head
            // draw head.
            arrowHeadStepRight++;
            dotColRight++;
            lc1.setLed(dotAddressRight, dotColRight, dotRowRight + arrowHeadStepRight, modeOnOff);
            lc1.setLed(dotAddressRight, dotColRight, dotRowRight - arrowHeadStepRight, modeOnOff);
            if (arrowHeadStepRight == 2) {
              //when drawing head is finished, initialize
              rightDotMatrixCycle++;
              dotAddressRight = 0;
              dotColRight = 7;
              dotRowRight = 7;
              arrowHeadStepRight = 0;
              reachedTipEndRight = false;
              onOffDotMatrixRight = true;
            }
          }// draw head finished
        } // last matrix finished
      }
    }//interval finished
  } // onOffDotMatrixLeft Finished
}//function finished

