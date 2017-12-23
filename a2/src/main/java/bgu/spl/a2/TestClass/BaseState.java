package bgu.spl.a2.TestClass;

import bgu.spl.a2.PrivateState;

import java.util.LinkedList;
import java.util.List;

public class BaseState extends PrivateState {

    public BaseState(){
        history = new LinkedList<String>();
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getName() {
        return null;
    }
}
