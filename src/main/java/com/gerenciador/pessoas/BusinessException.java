package com.gerenciador.pessoas;

public class BusinessException {

    public static void execptionCustom(String message) {
        try {
            throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
