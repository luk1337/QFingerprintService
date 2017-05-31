package com.qualcomm.qti.biometrics.fingerprint.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IFingerprintAuthenticatorListener extends IInterface {
    void onUserVerificationResult(byte[] nonce, int errorId, String authName, long p3, long p4, byte[] p5) throws RemoteException;

    void onCancel(byte[] p0) throws RemoteException;

    void onEnrollmentStatus(long p0, boolean p1) throws RemoteException;

    public abstract static class Stub extends Binder implements IFingerprintAuthenticatorListener {
        private static final String DESCRIPTOR = "com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticatorListener";

        private static final int TRANSACTION_onUserVerificationResult = 1;
        private static final int TRANSACTION_onCancel = 2;
        private static final int TRANSACTION_onEnrollmentStatus = 3;
        private static final int TRANSACTION_onWriteDescriptor = 1598968902;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IFingerprintAuthenticatorListener asInterface(IBinder binder) {
            if (binder == null) {
                return null;
            }

            final IInterface queryLocalInterface = binder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && queryLocalInterface instanceof IFingerprintAuthenticatorListener) {
                return (IFingerprintAuthenticatorListener) queryLocalInterface;
            }

            return new Proxy(binder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            switch (n) {
                case TRANSACTION_onUserVerificationResult:
                    parcel.enforceInterface(DESCRIPTOR);
                    onUserVerificationResult(parcel.createByteArray(), parcel.readInt(), parcel.readString(), parcel.readLong(), parcel.readLong(), parcel.createByteArray());
                    return true;
                case TRANSACTION_onCancel:
                    parcel.enforceInterface(DESCRIPTOR);
                    onCancel(parcel.createByteArray());
                    return true;
                case TRANSACTION_onEnrollmentStatus:
                    parcel.enforceInterface(DESCRIPTOR);
                    onEnrollmentStatus(parcel.readLong(), parcel.readInt() != 0);
                    return true;
                case TRANSACTION_onWriteDescriptor:
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(n, parcel, parcel2, n2);
            }
        }

        private static class Proxy implements IFingerprintAuthenticatorListener {
            private IBinder mRemote;

            Proxy(IBinder mRemote) {
                this.mRemote = mRemote;
            }

            public IBinder asBinder() {
                return mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override
            public void onUserVerificationResult(byte[] nonce, int errorId, String authName, long n, long n2, byte[] array) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(getInterfaceDescriptor());
                    obtain.writeByteArray(nonce);
                    obtain.writeInt(errorId);
                    obtain.writeString(authName);
                    obtain.writeLong(n);
                    obtain.writeLong(n2);
                    obtain.writeByteArray(array);

                    mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override
            public void onCancel(byte[] array) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(getInterfaceDescriptor());
                    obtain.writeByteArray(array);

                    mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override
            public void onEnrollmentStatus(long n, boolean b) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(getInterfaceDescriptor());
                    obtain.writeLong(n);
                    obtain.writeInt(b ? 1 : 0);

                    mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}