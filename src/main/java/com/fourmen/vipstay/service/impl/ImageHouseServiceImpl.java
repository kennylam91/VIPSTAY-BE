package com.fourmen.vipstay.service.impl;

import com.fourmen.vipstay.model.ImageOfHouse;
import com.fourmen.vipstay.repository.ImageHouseRepository;
import com.fourmen.vipstay.service.ImageHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageHouseServiceImpl implements ImageHouseService {
    @Autowired
    private ImageHouseRepository imageHouseRepository;

    @Override
    public List<ImageOfHouse> findAll() {
        return imageHouseRepository.findAll();
    }

    @Override
    public List<ImageOfHouse> findByHouseId(Long id) {
        return imageHouseRepository.findByHouseId(id);
    }

    @Override
    public List<String> getListImageUrlOfHouseByHouseId(Long id) {
        List<String> listImageUrl=new ArrayList<>();
        List<ImageOfHouse> imageOfHouses=imageHouseRepository.findByHouseId(id);
        for (ImageOfHouse image:imageOfHouses){
            listImageUrl.add(image.getImageUrl());
        }
        return listImageUrl;
    }

    @Override
    public ImageOfHouse findById(Long id) {
        return imageHouseRepository.findById(id).get();
    }

    @Override
    public void createImageHouse(ImageOfHouse imageOfHouse) {
        imageHouseRepository.save(imageOfHouse);
    }

    @Override
    public void updateImageHouse(ImageOfHouse imageOfHouse) {
        imageHouseRepository.save(imageOfHouse);
    }

    @Override
    public void deleteImageHouse(Long id) {
        imageHouseRepository.deleteById(id);
    }
}
