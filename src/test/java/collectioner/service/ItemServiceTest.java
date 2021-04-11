package collectioner.service;
import collectioner.repository.ItemRepository;
import collectioner.service.impl.ItemServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@RunWith(SpringRunner.class)
public class ItemServiceTest {
    @Autowired
    private ItemServiceImpl serviceToTest;

    @Mock
    ItemRepository mockItemRepository;

    @BeforeEach
    public void setUp() {
        serviceToTest = new ItemServiceImpl(mockItemRepository);
    }

    @Test
    public void testInitItems() {
        Mockito.when(mockItemRepository.count())
                .thenReturn(6L);

        serviceToTest.initItems();

        Assertions.assertEquals(6L, mockItemRepository.count());

    }
}
