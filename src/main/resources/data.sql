-- MySQL Script generated by MySQL Workbench
-- Thu Jul 20 13:37:42 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema paiza_learn_track
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema paiza_learn_track
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `paiza_learn_track` DEFAULT CHARACTER SET utf8 ;
USE `paiza_learn_track` ;

-- -----------------------------------------------------
-- Table `paiza_learn_track`.`team_users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paiza_learn_track`.`team_users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `email_address` VARCHAR(256) NOT NULL COMMENT 'メールアドレス',
  `name` VARCHAR(32) NULL COMMENT '名前',
  `delete_flg` TINYINT NOT NULL DEFAULT 0 COMMENT '削除フラグ：0.未削除、1.削除',
  `created` DATETIME NOT NULL COMMENT '作成日時',
  `updated` DATETIME NOT NULL COMMENT '更新日時',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `email_address_UNIQUE` (`email_address` ASC) VISIBLE)
ENGINE = InnoDB
COMMENT = 'チームユーザー';


-- -----------------------------------------------------
-- Table `paiza_learn_track`.`tasks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paiza_learn_track`.`tasks` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `course_id` INT NOT NULL COMMENT '講座ID',
  `course_name` VARCHAR(128) NOT NULL COMMENT '講座名',
  `lesson_id` VARCHAR(128) NULL COMMENT 'レッスンID',
  `lesson_name` VARCHAR(128) NULL COMMENT 'レッスン名',
  `chapter_id` INT NOT NULL COMMENT 'チャプターID（※paizaの場合は割り当てられたid、オリジナル課題の場合は10,000,000番台）',
  `chapter_name` VARCHAR(128) NULL COMMENT 'チャプター名',
  `exercise_num` INT NOT NULL DEFAULT 0 COMMENT '演習課題数（※csvファイルに記載がないので、目でpaizaのサイトを見て手動メンテ）',
  `task_types_id` INT NOT NULL COMMENT '課題種別ID（※1.paiza、2.オリジナル課題）',
  `learning_minutes` INT NOT NULL DEFAULT 0 COMMENT '学習時間（分）：算出した学習時間及び、割り当てた学習時間を登録',
  `created` DATETIME NOT NULL COMMENT '作成日時',
  `updated` DATETIME NOT NULL COMMENT '更新日時',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `uk_tasks1` (`chapter_id` ASC) VISIBLE)
ENGINE = InnoDB
COMMENT = '課題';


-- -----------------------------------------------------
-- Table `paiza_learn_track`.`team_user_task_progress`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paiza_learn_track`.`team_user_task_progress` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `email_address` VARCHAR(256) NOT NULL COMMENT 'メールアドレス',
  `course_id` INT NOT NULL COMMENT '講座ID',
  `course_name` VARCHAR(128) NOT NULL COMMENT '講座名',
  `course_completion_flag` VARCHAR(32) NOT NULL COMMENT '講座完了フラグ',
  `lesson_id` VARCHAR(128) NOT NULL COMMENT 'レッスンID',
  `lesson_name` VARCHAR(128) NOT NULL COMMENT 'レッスン名',
  `lesson_completion_flag` VARCHAR(32) NOT NULL COMMENT 'レッスン完了フラグ',
  `chapter_id` INT NOT NULL COMMENT 'チャプターID',
  `chapter_name` VARCHAR(128) NOT NULL COMMENT 'チャプター名',
  `chapter_completion_flag` VARCHAR(32) NOT NULL COMMENT 'チャプター完了フラグ',
  `chapter_start_datetime` DATETIME NOT NULL COMMENT 'チャプター受講開始日時',
  `chapter_last_access_datetime` DATETIME NOT NULL COMMENT 'チャプター最終受講日時',
  `report_date` DATE NOT NULL COMMENT '集計日（yyyy-MM-dd）',
  `created` DATETIME NOT NULL COMMENT '作成日時',
  `updated` DATETIME NOT NULL COMMENT '更新日時',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) INVISIBLE,
  INDEX `fk_team_user_task_progress_team_users_idx` (`email_address` ASC) VISIBLE,
  INDEX `fk_team_user_task_progress_tasks1_idx` (`chapter_id` ASC) VISIBLE,
  UNIQUE INDEX `uk_team_user_task_progress1` (`email_address` ASC, `chapter_id` ASC, `report_date` ASC) VISIBLE)
ENGINE = InnoDB
COMMENT = 'チームユーザー課題進捗';


-- -----------------------------------------------------
-- Table `paiza_learn_track`.`original_task_progress`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paiza_learn_track`.`original_task_progress` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `team_users_id` BIGINT NOT NULL,
  `chapter_id` INT NOT NULL COMMENT 'チャプターID',
  `task_progress_rate` DECIMAL(6,2) NOT NULL COMMENT '課題進捗率（%）：画面から入力された値を登録。',
  `report_date` DATE NOT NULL COMMENT '集計日（yyyy-MM-dd）',
  `created` DATETIME NOT NULL COMMENT '作成日時',
  `updated` DATETIME NOT NULL COMMENT '更新日時',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_original_task_progress_team_users1_idx` (`team_users_id` ASC) INVISIBLE,
  INDEX `fk_original_task_progress_tasks1_idx` (`chapter_id` ASC) INVISIBLE,
  UNIQUE INDEX `uk_original_task_progress` (`team_users_id` ASC, `chapter_id` ASC, `report_date` ASC) VISIBLE)
ENGINE = InnoDB
COMMENT = 'オリジナル課題進捗管理';


-- -----------------------------------------------------
-- Table `paiza_learn_track`.`progress_rates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paiza_learn_track`.`progress_rates` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `team_users_id` BIGINT NOT NULL COMMENT 'チームユーザーID',
  `course_id` INT NOT NULL COMMENT '講座ID',
  `course_name` VARCHAR(128) NOT NULL COMMENT '講座名',
  `lesson_id` VARCHAR(128) NOT NULL COMMENT 'レッスンID',
  `lesson_name` VARCHAR(128) NOT NULL COMMENT 'レッスン名',
  `achieved_learning_hours` DECIMAL(6,2) NOT NULL COMMENT '学習時間実績（時）',
  `total_learning_hours` DECIMAL(6,2) NOT NULL COMMENT '学習時間合計（時）：カリキュラムとして定義している学習時間の合計',
  `task_progress_rate` DECIMAL(6,2) NOT NULL COMMENT '課題進捗率（%）：『（「学習時間実績（時）」 / 「学習時間合計（時）） * 100」』の算出結果を登録。',
  `report_date` DATE NOT NULL COMMENT '集計日（yyyy-MM-dd）',
  `chapter_last_access_datetime` DATETIME NOT NULL COMMENT 'チャプター最終受講日時',
  `original_task_progress_id` BIGINT NULL COMMENT 'オリジナル課題進捗管理ID',
  `created` DATETIME NOT NULL COMMENT '作成日時',
  `updated` DATETIME NOT NULL COMMENT '更新日時',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_progress_rates_team_users1_idx` (`team_users_id` ASC) VISIBLE,
  UNIQUE INDEX `uk_progress_rates` (`team_users_id` ASC, `course_id` ASC, `lesson_id` ASC, `report_date` ASC) VISIBLE,
  INDEX `fk_progress_rates_tasks1_idx` (`course_id` ASC) VISIBLE,
  INDEX `fk_progress_rates_original_task_progress1_idx` (`original_task_progress_id` ASC) VISIBLE)
ENGINE = InnoDB
COMMENT = '課題進捗率';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
