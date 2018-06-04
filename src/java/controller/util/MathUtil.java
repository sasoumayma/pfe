/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.util;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author YOUNES
 */
public class MathUtil {

    public static Long calculerMax(Object[] res) {
        List<Long> res1 = (List<Long>) res[0];
        List<Long> res2 = (List<Long>) res[1];
        Long max2 = Collections.max(res2);
        Long max1 = Collections.max(res1);
        return Math.max(max1, max2);
    }

}
