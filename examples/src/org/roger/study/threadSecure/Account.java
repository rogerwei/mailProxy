package org.roger.study.threadSecure;

/**
 * Created with IntelliJ IDEA.
 * User: roger
 * Date: 13-8-7
 * Time: 下午6:05
 * To change this template use File | Settings | File Templates.
 *
 * From:http://www.iteye.com/topic/806990
 *
 * 一个线程执行临界区代码过程如下：
 1 获得同步锁
 2 清空工作内存
 3 从主存拷贝变量副本到工作内存
 4 对这些变量计算
 5 将变量从工作内存写回到主存
 6 释放锁
 */
public class Account {

    private int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public synchronized  void add(int num) {
        balance = balance + num;
    }

    public synchronized void withdraw(int num) {
        balance = balance - num;
    }

    public static void main(String[] args) throws InterruptedException {
        Account account = new Account(1000);
        Thread a = new Thread(new AddThread(account, 20), "add");
        Thread b = new Thread(new WithdrawThread(account, 20), "withdraw");
        a.start();
        b.start();
        a.join();
        b.join();
        System.out.println(account.getBalance());
    }

    static class AddThread implements Runnable {
        Account account;
        int     amount;

        public AddThread(Account account, int amount) {
            this.account = account;
            this.amount = amount;
        }

        public void run() {
            for (int i = 0; i < 200000; i++) {
                account.add(amount);
            }
        }
    }

    static class WithdrawThread implements Runnable {
        Account account;
        int     amount;

        public WithdrawThread(Account account, int amount) {
            this.account = account;
            this.amount = amount;
        }

        public void run() {
            for (int i = 0; i < 100000; i++) {
                account.withdraw(amount);
            }
        }
    }
}
