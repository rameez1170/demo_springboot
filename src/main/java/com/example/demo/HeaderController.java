package com.example.demo;

import com.sun.tools.javac.jvm.Items;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/header")
@Slf4j
public class HeaderController {


    @PostMapping
    public @ResponseBody
    Object logHeaderFooter(@RequestBody HeaderFooterDTO headerFooterDTO) {
        log.info("Incoming header footer {} ", headerFooterDTO);
        HeaderFooterEntity headerFooterEntity = HeaderFooterEntity.builder()
                .footerName(headerFooterDTO.getFooter().getName())
                .headerType(headerFooterDTO.getHeader().getType())
                .build();
        ///  save(headerFooterEntity) to get Header id;
        Integer headerId = 0;
        List<Item> itemsList = headerFooterDTO.getHeader().getItems();
        List<ItemEntity> itemEntities = itemsList.stream().map(item -> {
            ItemEntity itemEntity = new ItemEntity();
            itemEntity.setAge(item.getAge());
            itemEntity.setHeaderId(headerId);
            return itemEntity;
        }).collect(Collectors.toList());
/// save(itemEntities);

        return headerFooterDTO;
    }
}
