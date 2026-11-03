package org.example.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name="login_logs")
public class LoginLog {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="ip_address", nullable=false,length=45)
    private String ipAddress;

    @Column(name = "browser", length = 100)
    private String browser; // Örn: "Chrome 120"

    @Column(name = "operating_system", length = 100)
    private String operatingSystem; // Örn: "Windows 10", "iOS 17"

    @Column(name = "device_model", length = 100)
    private String deviceModel; // Örn: "Desktop", "iPhone", "Macinto

    @Column(name="logged_in_at")
    private Timestamp loggedInAt;
}
