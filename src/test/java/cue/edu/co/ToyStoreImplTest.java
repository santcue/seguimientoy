package cue.edu.co;

import cue.edu.co.mapping.dtos.ToyDto;
import cue.edu.co.mapping.mappers.ToyMapper;
import cue.edu.co.model.Toy;
import cue.edu.co.service.impl.ToyStoreImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ToyStoreImplTest {

    private ToyStoreImpl service;

    @Before
    public void setUp() {
        service = new ToyStoreImpl();
        File file = new File("data.dat");
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void addToy_Successful() throws Exception {
        String name = "Car";
        char type = 1;
        Integer price = 1500;
        Integer amount = 45;
        ToyDto toyToAdd = new ToyDto(name, type, price, amount);
        Toy toyExpected = ToyMapper.mapFromModel(toyToAdd);
        List<Toy> expected = List.of(toyExpected);

        List<Toy> result = service.addToy(toyToAdd);

        assertEquals(expected.size(), result.size());
        assertEquals(expected.get(0), result.get(0));
    }

    @Test
    public void loadStore_Successful() {
        service.addToy(new ToyDto("Car", '1', 1500.0, 45));

        ToyStoreImpl loadedStore = ToyStoreImpl.loadStore();

        assertEquals(1, loadedStore.getInventory().size());
    }

    @Test
    public void countToysByType_Successful() {
        service.addToy(new ToyDto("Car", '1', 1500.0, 45));
        service.addToy(new ToyDto("Train", '2', 2000.0, 30));
        service.addToy(new ToyDto("Doll", '1', 1200.0, 20));

        Map<Character, Integer> countMap = service.countToysByType();

        assertEquals(2, countMap.get('1').intValue());
        assertEquals(1, countMap.get('2').intValue());
    }

    @Test
    public void getTotalQuantity_EmptyInventory() {
        int totalQuantity = service.getTotalQuantity();

        assertEquals(0, totalQuantity);
    }

    @Test
    public void getTotalQuantity_NonEmptyInventory() {
        service.addToy(new ToyDto("Car", '1', 1500.0, 45));
        service.addToy(new ToyDto("Train", '2', 2000.0, 30));

        int totalQuantity = service.getTotalQuantity();

        assertEquals(75, totalQuantity);
    }

    @Test
    public void getTotalValue_EmptyInventory() {
        double totalValue = service.getTotalValue();

        assertEquals(0.0, totalValue, 0.0);
    }

    @Test
    public void getTotalValue_NonEmptyInventory() {
        service.addToy(new ToyDto("Car", '1', 1500.0, 2));
        service.addToy(new ToyDto("Train", '2', 2000.0, 3));

        double totalValue = service.getTotalValue();

        assertEquals(9000.0, totalValue, 0.0);
    }

    @Test
    public void decreaseStock_SufficientStock() {
        Toy toy = ToyMapper.mapFromModel(new ToyDto("Car", '1', 1500.0, 10));

        service.decreaseStock(toy, 5);

        assertEquals(5, toy.getQuantity());
    }

    @Test
    public void decreaseStock_InsufficientStock() {
        Toy toy = ToyMapper.mapFromModel(new ToyDto("Car", '1', 1500.0, 3));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.decreaseStock(toy, 5));

        assertEquals("Not enough stock to decrease.", exception.getMessage());
        assertEquals(3, toy.getQuantity());
    }

    @Test
    public void increaseStock() {
        Toy toy = ToyMapper.mapFromModel(new ToyDto("Car", '1', 1500.0, 3));

        service.increaseStock(toy, 5);

        assertEquals(8, toy.getQuantity());
    }

    @Test
    public void getTypeWithMostToys() {
        service.addToy(new ToyDto("Car", '1', 1500.0, 3));
        service.addToy(new ToyDto("Train", '2', 2000.0, 5));
        service.addToy(new ToyDto("Doll", '1', 1200.0, 8));

        char type = service.getTypeWithMostToys();

        assertEquals('1', type);
    }

    @Test
    public void getTypeWithLeastToys() {
        service.addToy(new ToyDto("Car", '1', 1500.0, 3));
        service.addToy(new ToyDto("Train", '2', 2000.0, 5));
        service.addToy(new ToyDto("Doll", '1', 1200.0, 8));

        char type = service.getTypeWithLeastToys();

        assertEquals('2', type);
    }

    @Test
    public void getToysWithValueGreaterThan() {
        service.addToy(new ToyDto("Car", '1', 1500.0, 3));
        service.addToy(new ToyDto("Train", '2', 2000.0, 5));
        service.addToy(new ToyDto("Doll", '1', 1200.0, 8));

        List<Toy> toys = service.getToysWithValueGreaterThan(1400.0);

        assertEquals(2, toys.size());
    }

}
