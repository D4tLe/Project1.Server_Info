/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package INFO;

import java.util.Comparator;

/**
 *
 * @author admin
 */
public class def implements Comparator<PROCESS_INFO> {
        public int compare(PROCESS_INFO a, PROCESS_INFO b) {
            if (a.getMemoryUsage() > b.getMemoryUsage()) return 1;
            else if (a.getMemoryUsage() == b.getMemoryUsage()) return 0;
            else return -1;
        }
    }
