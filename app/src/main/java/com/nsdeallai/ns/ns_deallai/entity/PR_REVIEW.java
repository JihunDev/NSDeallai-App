package com.nsdeallai.ns.ns_deallai.entity;

/**
 * Created by SangSang on 2015-09-08.
 */
public class PR_REVIEW {

    int pr_id;
    String o_id;
    int c_id;
    int p_id;
    String u_id;
    String pr_content;
    float pr_star;

    public PR_REVIEW() {
    }

    public PR_REVIEW(int p_id) {
        this.p_id = p_id;
    }

    public PR_REVIEW(String o_id) {
        this.o_id = o_id;
    }

    public PR_REVIEW(int p_id, String u_id) {
        this.p_id = p_id;
        this.u_id = u_id;
    }

    public PR_REVIEW(String o_id, String pr_content, float pr_star) {
        this.o_id = o_id;
        this.pr_content = pr_content;
        this.pr_star = pr_star;
    }

    public PR_REVIEW(int pr_id, String o_id, int c_id, int p_id, String u_id, String pr_content, float pr_star) {
        this.pr_id = pr_id;
        this.o_id = o_id;
        this.c_id = c_id;
        this.p_id = p_id;
        this.u_id = u_id;
        this.pr_content = pr_content;
        this.pr_star = pr_star;
    }

    public int getPr_id() {
        return pr_id;
    }

    public void setPr_id(int pr_id) {
        this.pr_id = pr_id;
    }

    public String getO_id() {
        return o_id;
    }

    public void setO_id(String o_id) {
        this.o_id = o_id;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getPr_content() {
        return pr_content;
    }

    public void setPr_content(String pr_content) {
        this.pr_content = pr_content;
    }

    public float getPr_star() {
        return pr_star;
    }

    public void setPr_star(float pr_star) {
        this.pr_star = pr_star;
    }

    @Override
    public String toString() {
        return "PR_REVIEW{" +
                "pr_id=" + pr_id +
                ", o_id='" + o_id + '\'' +
                ", c_id=" + c_id +
                ", p_id=" + p_id +
                ", u_id='" + u_id + '\'' +
                ", pr_content='" + pr_content + '\'' +
                ", pr_star=" + pr_star +
                '}';
    }
}
