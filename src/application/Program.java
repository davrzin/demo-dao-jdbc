package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== Test 1: Seller findByid ===\n");
        Seller seller = sellerDao.findById(3);

        System.out.println(seller + "\n");

        System.out.println("=== Test 2: Seller findByDepartment ===\n");

        Department  department = new Department(2, null);

        List<Seller> list = sellerDao.findByDepartment(department);

        for (Seller obj : list) {
            System.out.println(obj + "\n");
        }

        System.out.println("=== Test 3: Seller findAll ===\n");

        list = sellerDao.findAll();

        for (Seller obj : list) {
            System.out.println(obj + "\n");
        }

        System.out.println("=== Test 4: Seller insert ===\n");

        Seller seller2 = new Seller(
            null,
            "Greg",
            "greg@gmail.com",
            new Date(),
            4000.00,
            department
        );

        sellerDao.insert(seller2);

        System.out.println("Inserted! New id: " + seller2.getId());


        System.out.println("=== Test 5: Seller update ===\n");

        seller = sellerDao.findById(1);
        seller.setName("Martha Waine");
        sellerDao.update(seller);

        System.out.println("Update Completed\n");

        System.out.println("=== Test 6: Seller delete ===\n");
        
        System.out.print("Enter id for seller delete test: ");
        int id = sc.nextInt();
        sellerDao.deleteById(id);
        System.out.println("Delete Completed");

        sc.close();
    }
   
}
