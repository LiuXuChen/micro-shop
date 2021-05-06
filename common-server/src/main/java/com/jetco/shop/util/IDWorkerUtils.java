package com.jetco.shop.util;

import com.jetco.shop.exception.ParameterException;

/**
 * <p>
 *   分布式ID生成算法工具类
 *
 *   优点：
 *      1、高性能、高可用
 *      2、容量大
 *      3、ID自增
 *   缺点：
 *      依赖与系统时间的一致性，如果系统时间改变，可能造成ID冲突或重复
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-02-02
 */
public class IDWorkerUtils {

    /**
     * 起始的时间戳
     */
    private static final long START_STAMP = 1480166465631L;

    //每一部分占用的位数
    /**
     * 序列号占用的位数
     */
    private static final long SEQUENCE_BIT = 12;
    /**
     * 机器标识占用的位数
     */
    private static final long MACHINE_BIT = 5;
    /**
     * 数据中心占用的位数
     */
    private static final long DATACENTER_BIT = 5;

    /**
     * 每一部分的最大值
     */
    private static final long MAX_DATACENTER_NUM = ~(-1L << DATACENTER_BIT);
    private static final long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private static final long MACHINE_LEFT = SEQUENCE_BIT;
    private static final long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private static final long TIMESTAMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    /**
     * 数据中心
     */
    private final long datacenterId;
    /**
     * 机器标识
     */
    private final long machineId;
    /**
     * 序列号
     */
    private long sequence = 0L;
    /**
     * 上一次时间戳
     */
    private long lastTimeStamp = -1L;

    public IDWorkerUtils(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new ParameterException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new ParameterException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 生成下一个long类型ID
     *
     * @return id
     */
    public synchronized long generateNextLongId() {
        long currTimeStamp = getNewTimeStamp();
        if (currTimeStamp < lastTimeStamp) {
            throw new ParameterException("Clock moved backwards.  Refusing to generate id");
        }
        if (currTimeStamp == lastTimeStamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currTimeStamp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }
        lastTimeStamp = currTimeStamp;
        //时间戳部分
        return (currTimeStamp - START_STAMP) << TIMESTAMP_LEFT
                //数据中心部分
                | datacenterId << DATACENTER_LEFT
                //机器标识部分
                | machineId << MACHINE_LEFT
                //序列号部分
                | sequence;
    }

    /**
     * 生成下一个String类型ID
     * @return id
     */
    public synchronized String generateNextStrId() {
        return String.valueOf(this.generateNextLongId());
    }

    private long getNextMill() {
        long timeStamp = getNewTimeStamp();
        while (timeStamp <= lastTimeStamp) {
            timeStamp = getNewTimeStamp();
        }
        return timeStamp;
    }

    private long getNewTimeStamp() {
        return System.currentTimeMillis();
    }

}
