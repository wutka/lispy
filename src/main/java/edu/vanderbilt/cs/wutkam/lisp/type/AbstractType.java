package edu.vanderbilt.cs.wutkam.lisp.type;

/**
 * Created with IntelliJ IDEA.
 * User: mark
 * Date: 5/24/21
 * Time: 4:47 PM
 */
public class AbstractType extends Type {
    public final String tag;

    public AbstractType() {
        this.tag = "'a";
    }

    public AbstractType(String tag) {
        this.tag = tag;
    }

    public boolean isAbstract() {
        return true;
    }

    public String toString() {
        return tag;
    }

}
