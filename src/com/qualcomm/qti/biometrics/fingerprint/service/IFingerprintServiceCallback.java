package com.qualcomm.qti.biometrics.fingerprint.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IFingerprintServiceCallback extends IInterface {
    void onMatched(int p0, final String p1) throws RemoteException;

    void onRemoved(int p0) throws RemoteException;

    void onError(int p0) throws RemoteException;

    void onStatus(int p0, byte[] p1) throws RemoteException;

    void onEnrolled(int p0, int p1) throws RemoteException;

    public abstract static class Stub extends Binder implements IFingerprintServiceCallback {
        private static final String DESCRIPTOR = "com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintServiceCallback";

        private static final int TRANSACTION_onMatched = 1;
        private static final int TRANSACTION_onRemoved = 2;
        private static final int TRANSACTION_onError = 3;
        private static final int TRANSACTION_onStatus = 4;
        private static final int TRANSACTION_onEnrolled = 5;
        private static final int TRANSACTION_onWriteDescriptor = 1598968902;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IFingerprintServiceCallback asInterface(IBinder binder) {
            if (binder == null) {
                return null;
            }

            IInterface queryLocalInterface = binder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && queryLocalInterface instanceof IFingerprintServiceCallback) {
                return (IFingerprintServiceCallback) queryLocalInterface;
            }

            return new Proxy(binder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            switch (n) {
                case TRANSACTION_onMatched:
                    parcel.enforceInterface(DESCRIPTOR);
                    onMatched(parcel.readInt(), parcel.readString());
                    return true;
                case TRANSACTION_onRemoved:
                    parcel.enforceInterface(DESCRIPTOR);
                    onRemoved(parcel.readInt());
                    return true;
                case TRANSACTION_onError:
                    parcel.enforceInterface(DESCRIPTOR);
                    onError(parcel.readInt());
                    return true;
                case TRANSACTION_onStatus:
                    parcel.enforceInterface(DESCRIPTOR);
                    onStatus(parcel.readInt(), parcel.createByteArray());
                    return true;
                case TRANSACTION_onEnrolled:
                    parcel.enforceInterface(DESCRIPTOR);
                    onEnrolled(parcel.readInt(), parcel.readInt());
                    return true;
                case TRANSACTION_onWriteDescriptor:
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(n, parcel, parcel2, n2);
            }
        }

        private static class Proxy implements IFingerprintServiceCallback {
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
            public void onMatched(int n, String s) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(getInterfaceDescriptor());
                    obtain.writeInt(n);
                    obtain.writeString(s);

                    mRemote.transact(TRANSACTION_onMatched, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override
            public void onRemoved(int n) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(getInterfaceDescriptor());
                    obtain.writeInt(n);

                    mRemote.transact(TRANSACTION_onRemoved, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override
            public void onError(int n) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(getInterfaceDescriptor());
                    obtain.writeInt(n);

                    mRemote.transact(TRANSACTION_onError, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override
            public void onStatus(int n, byte[] array) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(getInterfaceDescriptor());
                    obtain.writeInt(n);
                    obtain.writeByteArray(array);

                    mRemote.transact(TRANSACTION_onStatus, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override
            public void onEnrolled(int n, int n2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(getInterfaceDescriptor());
                    obtain.writeInt(n);
                    obtain.writeInt(n2);

                    mRemote.transact(TRANSACTION_onEnrolled, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}