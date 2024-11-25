<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.ssafy.cafe.model.dao.ChatDao">

    <resultMap id="ChatResultMap" type="com.ssafy.cafe.model.dto.Chat">
        <id property="id" column="id" />
        <result property="userId" column="user_id" />
        <result property="level" column="level" />
        <result property="plan" column="plan" />
        <result property="progress" column="progress" />
        <result property="alertIntervalMinutes" column="alert_interval_minutes" />
        <result property="nextAlarmTime" column="next_alarm_time" />
        <result property="createdAt" column="created_at" />
        <result property="updatedAt" column="updated_at" />
    </resultMap>

    <insert id="insert" parameterType="com.ssafy.cafe.model.dto.Chat" useGeneratedKeys="true" keyProperty="id">
        INSERT INTO t_chat (user_id, level, plan, progress, alert_interval_minutes, next_alarm_time)
        VALUES (#{userId}, #{level}, #{plan}, #{progress}, #{alertIntervalMinutes}, #{nextAlarmTime});
    </insert>

    <update id="update" parameterType="com.ssafy.cafe.model.dto.Chat">
        UPDATE t_chat
        SET
            user_id = #{userId},
            level = #{level},
            plan = #{plan},
            progress = #{progress},
            alert_interval_minutes = #{alertIntervalMinutes},
            next_alarm_time = #{nextAlarmTime},
            updated_at = CURRENT_TIMESTAMP
        WHERE id = #{id};
    </update>

    <select id="selectById" parameterType="int" resultMap="ChatResultMap">
        SELECT * FROM t_chat WHERE id = #{id};
    </select>

    <select id="selectAll" resultMap="ChatResultMap">
        SELECT * FROM t_chat;
    </select>

    <!-- next_alarm_time이 현재 시각 이전인 레코드를 선택 -->
    <select id="selectPendingAlarms" resultMap="ChatResultMap">
        SELECT * FROM t_chat WHERE next_alarm_time IS NOT NULL AND next_alarm_time <= NOW();
    </select>

    <delete id="delete" parameterType="int">
        DELETE FROM t_chat WHERE id = #{id};
    </delete>

</mapper>
