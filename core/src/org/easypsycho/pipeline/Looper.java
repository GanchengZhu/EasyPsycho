package org.easypsycho.pipeline;

import java.util.ArrayList;
import java.util.List;

public class Looper {
    List<Block> blockList;

    Looper(){
        blockList = new ArrayList<>();
    }

    public void addBlock(Block block){
         blockList.add(block);
    }

    public void removeBlock(int n){
        blockList.remove(n);
    }

    public void removeBlock(Block block){
        blockList.remove(block);
    }

    public Block getBlock(int n){
        return blockList.get(n);
    }


}
