package br.com.letscode.produto.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter @Setter
public class ErroMsg {
    private List<String> msg;

    public ErroMsg(String msg) {
        this.msg = Arrays.asList(msg);
    }

    public ErroMsg(List<String> msg){
        this.msg = msg;
    }

}
