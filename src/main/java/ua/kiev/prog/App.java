package ua.kiev.prog;

import javax.persistence.*;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class App {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        emf = Persistence.createEntityManagerFactory("JPATest");
        em = emf.createEntityManager();
        try {
            while (true) {
                System.out.println("1 Add new dish");
                System.out.println("2 Delete dish");
                System.out.println("3 Change menu");
                System.out.println("4 View menu");
                System.out.println("5 Only with discount");
                System.out.println("-->");

                String s = sc.nextLine();
                switch (s) {
                    case "1":
                        addNewDish(sc);
                        break;
                    case "2":
                        deleteDish(sc);
                        break;
                    case "3":
                        changeMenu(sc);
                        break;
                    case "4":
                        viewmenu();
                        break;
                    case "5":
                        onlyDiscount();
                        break;
                    default:
                        return;
                }
            }
        } finally {
            sc.close();
            em.close();
            emf.close();
        }
    }

    public static void addNewDish(Scanner sc) {
        System.out.println("Enter new Dish name");
        String name = sc.nextLine();
        System.out.println("Enter dish price");
        String sPrice = sc.nextLine();
        int price = Integer.parseInt(sPrice);
        System.out.println("Enter dish weight");
        String sWeight = sc.nextLine();
        int weight = Integer.parseInt(sWeight);
        System.out.println("Discount");
        System.out.println("If dish with discount enter 1");
        System.out.print("If dish without discount enter 2: ");
        String sDiscount = sc.nextLine();
        boolean discount = false;
        if ("1".equals(sDiscount))
            discount = true;
        em.getTransaction().begin();
        try {
            Menu m = new Menu(name, price, weight, discount);
            em.persist(m);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }

    }

    public static void deleteDish(Scanner sc) {
        System.out.println("Enter Dish id: ");
        String sId = sc.nextLine();
        long id = Long.parseLong(sId);
        Menu m = em.find(Menu.class, id);
        if (m == null) {
            System.out.println("Dish not found");
            return;
        }
        em.getTransaction().begin();
        try {
            em.remove(m);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    public static void changeMenu(Scanner sc) {
        System.out.println("Enter Dish name");
        String name = sc.nextLine();
        System.out.println("Enter new price");
        String sInt = sc.nextLine();
        int price = Integer.parseInt(sInt);
        Menu c = null;
        try {
            Query query = em.createQuery("SELECT  c from Menu  c where c.name = :name", Menu.class);
            query.setParameter("name", name);
            c = (Menu) query.getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("Dish not found ");
            return;
        } catch (NonUniqueResultException ex) {
            System.out.println("No Unique result");
            return;
        }

        em.getTransaction().begin();
        try {
            c.setPrice(price);
            em.getTransaction().commit();
        } catch (Exception ex){
            em.getTransaction().rollback();
        }
    }




    public static void viewmenu(){
        Query query = em.createQuery("select c from Menu c", Menu.class);
        List<Menu> list = (List<Menu>) query.getResultList();
        for (Menu m : list){
            System.out.println(m);
        }
    }

    public static void onlyDiscount () {
        Query query = em.createQuery("select c from Menu c where c.discount = true ", Menu.class);
        List<Menu> list = (List<Menu>) query.getResultList();
        for (Menu m : list){
            System.out.println(m);
        }
    }

}