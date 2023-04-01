package com.example.newgensociety;

public class Notice {
        String cm_name, subject, notice, date, notice_code;
        boolean removed;

        public Notice() {
        }

        public Notice(String cm_name, String subject, String notice, String date, String code, boolean removed) {
                this.cm_name = cm_name;
                this.subject = subject;
                this.notice = notice;
                this.date = date;
                this.notice_code = code;
                this.removed = removed;
        }

        public boolean isRemoved() {
                return removed;
        }

        public void setRemoved(boolean removed) {
                this.removed = removed;
        }

        public String getNotice_code() {
                return notice_code;
        }

        public void setNotice_code(String notice_code) {
                this.notice_code = notice_code;
        }

        public String getCm_name() {
                return cm_name;
        }

        public void setCm_name(String cm_name) {
                this.cm_name = cm_name;
        }

        public String getNotice() {
                return notice;
        }

        public void setNotice(String notice) {
                this.notice = notice;
        }

        public String getSubject() {
                return subject;
        }

        public void setSubject(String subject) {
                this.subject = subject;
        }

        public String getDate() {return date;}

        public void setDate(String date) {this.date = date;}
}
