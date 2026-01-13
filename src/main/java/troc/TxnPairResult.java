package troc;

import java.util.ArrayList;

public class TxnPairResult {
    private ArrayList<StatementCell> order;
    private ArrayList<Object> finalState;
    private boolean isDeadBlock;

    public TxnPairResult() { }

    public TxnPairResult(ArrayList<StatementCell> order, ArrayList<Object> finalState, boolean isDeadBlock) {
        this.order = order;
        this.finalState = finalState;
        this.isDeadBlock = isDeadBlock;
    }

    public void setOrder(ArrayList<StatementCell> order) {
        this.order = order;
    }

    public ArrayList<StatementCell> getOrder() {
        return order;
    }

    public void setFinalState(ArrayList<Object> finalState) {
        this.finalState = finalState;
    }

    public ArrayList<Object> getFinalState() {
        return finalState;
    }

    public void setDeadBlock(boolean deadBlock) {
        isDeadBlock = deadBlock;
    }

    public boolean isDeadBlock() {
        return isDeadBlock;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Result:\nOrder:").append(order).append("\n");
        sb.append("Query Results:\n");
        // @yhy 记录 valid operation 占比
        for (StatementCell stmt : order) {
            if (stmt.statement.contains("SELECT")) {
                Main.totalOperations += 1;
                if (stmt.result != null && !stmt.result.isEmpty()) {
                    Main.validOperations += 1;
                }
            }
            sb.append("\t").append(stmt.tx.txId).append("-").append(stmt.statementId)
                    .append(": ").append(stmt.result).append("\n");
        }
        Double validOpsRate = Main.totalOperations == 0 ? 0.0 :
                (Double.valueOf(Main.validOperations) / Double.valueOf(Main.totalOperations)) * 100;
        sb.append("FinalState: ").append(finalState).append("\n");
        sb.append("DeadBlock: ").append(isDeadBlock).append("\n");
        sb.append("totalValid operations: ").append(Main.validOperations).append("/")
                .append(Main.totalOperations).append(" (").append(String.format("%.2f", validOpsRate)).append("%)\n");
        return sb.toString();
    }
}
