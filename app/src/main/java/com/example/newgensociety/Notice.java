package com.example.newgensociety;

public class Notice {
        String cm_name, subject, notice;

        public Notice() {
        }

        public Notice(String cm_name, String subject, String notice) {
                this.cm_name = cm_name;
                this.subject = subject;
                this.notice = notice;
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
}