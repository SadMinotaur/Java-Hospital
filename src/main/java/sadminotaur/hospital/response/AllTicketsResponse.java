package sadminotaur.hospital.response;

import java.util.Arrays;

public class AllTicketsResponse {

    private TicketResponse[] ticketResponses;
    private CommissionArray[] commissionArrays;

    public AllTicketsResponse() {
    }

    public AllTicketsResponse(TicketResponse[] ticketResponses, CommissionArray[] commissionArrays) {
        this.ticketResponses = ticketResponses;
        this.commissionArrays = commissionArrays;
    }

    public TicketResponse[] getTicketResponses() {
        return ticketResponses;
    }

    public void setTicketResponses(TicketResponse[] ticketResponses) {
        this.ticketResponses = ticketResponses;
    }

    public CommissionArray[] getCommissionArrays() {
        return commissionArrays;
    }

    public void setCommissionArrays(CommissionArray[] commissionArrays) {
        this.commissionArrays = commissionArrays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AllTicketsResponse)) return false;
        AllTicketsResponse that = (AllTicketsResponse) o;
        return Arrays.equals(getTicketResponses(), that.getTicketResponses()) &&
                Arrays.equals(getCommissionArrays(), that.getCommissionArrays());
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(getTicketResponses());
        result = 31 * result + Arrays.hashCode(getCommissionArrays());
        return result;
    }

    @Override
    public String toString() {
        return "TicketsResponse{" +
                "ticketResponses=" + Arrays.toString(ticketResponses) +
                ", commissionArrays=" + Arrays.toString(commissionArrays) +
                '}';
    }
}
