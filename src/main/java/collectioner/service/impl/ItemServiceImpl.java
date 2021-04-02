package collectioner.service.impl;

import collectioner.model.entity.ItemEntity;
import collectioner.repository.ItemRepository;
import collectioner.service.ItemService;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void initItems() {

        if (itemRepository.findAll().isEmpty()) {
            itemRepository.save(new ItemEntity("Weapon Level 0", 0, 0, 0, 0));
            itemRepository.save(new ItemEntity("Weapon Level 1", 10, 20, 10, 10));
            itemRepository.save(new ItemEntity("Weapon Level 2", 30, 60, 30, 30));

            itemRepository.save(new ItemEntity("Shield Level 0", 0, 0, 0, 0));
            itemRepository.save(new ItemEntity("Shield Level 1", 50, 5, 20, 10));
            itemRepository.save(new ItemEntity("Shield Level 2", 150, 15, 60, 30));
        }
    }
}
