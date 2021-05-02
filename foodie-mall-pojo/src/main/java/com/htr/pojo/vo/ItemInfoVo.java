package com.htr.pojo.vo;

import com.htr.pojo.Items;
import com.htr.pojo.ItemsImg;
import com.htr.pojo.ItemsParam;
import com.htr.pojo.ItemsSpec;

import java.util.List;

/**
 * @Author: T. He
 * @Date: 2021/5/1
 */
public class ItemInfoVo {

    private Items item;
    private List<ItemsImg> itemImgList;
    private  List<ItemsSpec> itemSpecList;
    private ItemsParam itemParams;

    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        this.item = item;
    }

    public List<ItemsImg> getItemImgList() {
        return itemImgList;
    }

    public void setItemImgList(List<ItemsImg> itemImgList) {
        this.itemImgList = itemImgList;
    }

    public List<ItemsSpec> getItemSpecList() {
        return itemSpecList;
    }

    public void setItemSpecList(List<ItemsSpec> itemSpecList) {
        this.itemSpecList = itemSpecList;
    }

    public ItemsParam getItemParams() {
        return itemParams;
    }

    public void setItemParams(ItemsParam itemParams) {
        this.itemParams = itemParams;
    }
}
