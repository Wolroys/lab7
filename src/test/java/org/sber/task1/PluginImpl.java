package org.sber.task1;


import lombok.Data;

@Data
public class PluginImpl implements Plugin{
    @Override
    public void doUseful() {
        System.out.println("Doing good");
    }
}
