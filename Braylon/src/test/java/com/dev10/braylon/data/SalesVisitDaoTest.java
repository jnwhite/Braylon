/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dev10.braylon.data;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Joe Gonzalez
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SalesVisitDaoTest {
    
    @Autowired
    private SalesVisitDao salesVisitDao;
    
    public SalesVisitDaoTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of findSalesVisitsAfter method, of class SalesVisitDao.
     */
    @Test
    public void testFindSalesVisitsAfter() {
    }

    /**
     * Test of findSalesVisitsByUserAfter method, of class SalesVisitDao.
     */
    @Test
    public void testFindSalesVisitsByUserAfter() {
    }

    /**
     * Test of findSalesByUSername method, of class SalesVisitDao.
     */
    @Test
    public void testFindSalesByUSername() {
    }

}
