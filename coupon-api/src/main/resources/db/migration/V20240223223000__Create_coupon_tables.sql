CREATE TABLE IF NOT EXISTS `coupon`.`coupons`
(
    id                   bigint(20)   NOT NULL AUTO_INCREMENT,
    title                varchar(255) NOT NULL,
    coupon_type          varchar(255) NOT NULL,
    total_quantity       bigint(20)   NULL,
    issued_quantity      bigint(20)   NOT NULL,
    discount_amount      bigint(20)   NOT NULL,
    min_available_amount bigint(20)   NOT NULL,
    date_issue_start     datetime     NOT NULL,
    date_issue_end       datetime     NOT NULL,
    date_created         datetime     NOT NULL,
    date_updated         datetime     NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS `coupon`.`coupon_issues`
(
    id           bigint(20) NOT NULL AUTO_INCREMENT,
    title        bigint(20) NOT NULL,
    type         bigint(20) NOT NULL,
    date_issued  datetime   NOT NULL,
    date_used    datetime   NULL,
    date_created datetime   NOT NULL,
    date_updated datetime   NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
