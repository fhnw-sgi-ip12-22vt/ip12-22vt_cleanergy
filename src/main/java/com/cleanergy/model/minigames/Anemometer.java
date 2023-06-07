package com.cleanergy.model.minigames;

import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.procimg.Register;
import com.ghgande.j2mod.modbus.util.SerialParameters;

public class Anemometer {

  private static ModbusSerialMaster master;

  // connects to master and is ready for measuring.
  public static void init() {
    try {
      SerialParameters params = new SerialParameters();
      params.setPortName("ttyUSB0");
      master = new ModbusSerialMaster(params);
      // reading holding registers
      master.connect();
    } catch (Exception e) {
      //System.out.println("Our error message " + e.getMessage());
      e.printStackTrace();
    }
  }

  public static int measureAnemoSpeed() {
    int windSpeed = 0;

    try {
      Register[] slaveResponse = master.readMultipleRegisters(2, 0, 1);
      for (Register reg : slaveResponse) {
        System.out.println(reg.getValue());
        windSpeed = reg.getValue();
      }
    } catch (Exception e) {
      //System.out.println("Our error message " + e.getMessage());
      e.printStackTrace();
    }

    return windSpeed;
  }
}
