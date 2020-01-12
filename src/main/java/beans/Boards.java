
package beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Boards {

    @SerializedName("totalMembersPerBoard")
    @Expose
    public TotalMembersPerBoard totalMembersPerBoard;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("totalMembersPerBoard", totalMembersPerBoard).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(totalMembersPerBoard).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Boards) == false) {
            return false;
        }
        Boards rhs = ((Boards) other);
        return new EqualsBuilder().append(totalMembersPerBoard, rhs.totalMembersPerBoard).isEquals();
    }

}
