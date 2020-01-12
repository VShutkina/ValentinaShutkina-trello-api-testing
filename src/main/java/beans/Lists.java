
package beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Lists {

    @SerializedName("openPerBoard")
    @Expose
    public OpenPerBoard_ openPerBoard;
    @SerializedName("totalPerBoard")
    @Expose
    public TotalPerBoard_ totalPerBoard;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("openPerBoard", openPerBoard).append("totalPerBoard", totalPerBoard).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(totalPerBoard).append(openPerBoard).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Lists) == false) {
            return false;
        }
        Lists rhs = ((Lists) other);
        return new EqualsBuilder().append(totalPerBoard, rhs.totalPerBoard).append(openPerBoard, rhs.openPerBoard).isEquals();
    }

}
