
package beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CustomFields {

    @SerializedName("perBoard")
    @Expose
    public PerBoard__ perBoard;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("perBoard", perBoard).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(perBoard).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CustomFields) == false) {
            return false;
        }
        CustomFields rhs = ((CustomFields) other);
        return new EqualsBuilder().append(perBoard, rhs.perBoard).isEquals();
    }

}
