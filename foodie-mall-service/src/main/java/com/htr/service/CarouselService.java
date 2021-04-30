package com.htr.service;

import com.htr.pojo.Carousel;

import java.util.List;

/**
 * @Author: T. He
 * @Date: 2021/4/30
 */
public interface CarouselService {

    public List<Carousel> queryAll(Integer isShow);

}
