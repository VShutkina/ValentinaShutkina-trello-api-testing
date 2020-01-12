
package beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Cards {

    @SerializedName("openPerBoard")
    @Expose
    public OpenPerBoard openPerBoard;
    @SerializedName("totalPerBoard")
    @Expose
    public TotalPerBoard totalPerBoard;

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
        if ((other instanceof Cards) == false) {
            return false;
        }
        Cards rhs = ((Cards) other);
        return new EqualsBuilder().append(totalPerBoard, rhs.totalPerBoard).append(openPerBoard, rhs.openPerBoard).isEquals();
    }

}
