package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        
        DepartmentDao depDao = DaoFactory.createDepartmentDao();

        System.out.println("=== Test 1: Department findByid ===\n");
        Department dep = depDao.findById(3);

        System.out.println(dep + "\n");

        System.out.println("=== Test 2: Department findAll ===\n");

        List<Department> list = depDao.findAll();

        for (Department department2 : list) {
            System.out.println(department2 + "\n");
        }

        System.out.println("=== Test 3: Department insert ===\n");

        Department dep1 = new Department(null, "D1");
        depDao.insert(dep1);
        System.out.println("Inserted! New id: " + dep1.getId() + "\n");

        System.out.println("=== Test 4: Department insert ===\n");

        Department dep2 = depDao.findById(8);
        dep2.setName("Foods");
        depDao.update(dep2);

        System.out.println("Update Completed\n");

        System.out.println("=== Test 5: Department delete ===\n");

        System.out.print("Enter id for department delete test: ");
        int id = sc.nextInt();
        depDao.deleteById(id);
        System.out.println("Delete Completed");
        
        sc.close();

    }
}
