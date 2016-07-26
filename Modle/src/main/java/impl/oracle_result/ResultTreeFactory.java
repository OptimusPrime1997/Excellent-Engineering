package impl.oracle_result;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Administrator on 2016/7/18.
 */
public class ResultTreeFactory {

    private List<Object> list = new ArrayList<Object>();
    private final String[] priority = {"not","and","or","("}; //运算符的优先级按降序排列

    public void put(String obj){
       list.add(obj);
    }

    public void put(Result rs){
        list.add(rs);
    }


    public Result createResultTree(){
        Stack<String> operation = new Stack<String>();
        Stack<Result> element = new Stack<Result>();
        for(Object obj : list){
            String type = obj.getClass().getName();
            if(type == String.class.getName()){
                //System.out.println((String) obj);
                if(obj.equals("(")){
                    operation.push("(");
                }else if(obj.equals(")")){
                    String str = operation.pop();
                    while(!str.equals("(")){
                        this.createResultByOperation(str,element);
                        str = operation.pop();
                    }
                }else{
                    this.popHighPriorityOperation(operation,element,(String) obj);
                }
            }else{
                element.push((Result)obj);
            }
        }
        while(!operation.empty()){
            String op = operation.pop();
            this.createResultByOperation(op,element);
        }
        return element.pop();
    }


    private void popHighPriorityOperation(Stack<String> operations,Stack<Result> elements,String operation){
        if(operations.empty()){
            operations.push(operation);
            return;
        }

        String op = operations.pop();
        while(this.hasHigherpriority(op,operation)){
            this.createResultByOperation(op,elements);
            if(operations.empty()){
                operations.push(operation);
                return;
            }
            op = operations.pop();
        }
        operations.push(op);
        operations.push(operation);
    }

    /**
     * 将操作数占中的Result根据运算符组装起来，重新压入操作数栈中
     * @param operation 操作符
     * @param elements 操作数栈
     */
    private void createResultByOperation(String operation,Stack<Result> elements){
       //System.out.println(elements.size());
        switch(operation){
            case "and":
                Result and1 = elements.pop();
                Result and2 = elements.pop();
                Result andRs = new AndOperation(and1,and2);
                elements.push(andRs);
                return;
            case "or":
                Result or1 = elements.pop();
                Result or2 = elements.pop();
                Result orRs = new OrOperation(or1,or2);
                elements.push(orRs);
                return;
            case "not":
                Result not = elements.pop();
                Result notRs = new NotOperation(not);
                elements.push(notRs);
                return;
        }
    }
    /**
     * 如果operation1的优先级大于operation2的，就返回true，反之，返回false
     * @param operation1 运算符1
     * @param operation2 运算符2
     * @return
     */
    private boolean hasHigherpriority(String operation1,String operation2){
        for(int x = 0 ; x < priority.length ; x++){
            if(operation2.equals(priority[x])){
                return false;
            }else if(operation1.equals(priority[x])){
                return true;
            }
        }
        return false;
    }
}
