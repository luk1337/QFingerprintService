package com.qualcomm.qti.biometrics.fingerprint.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IFingerprintService extends IInterface {
    void enroll(int p0, IFingerprintServiceCallback p1) throws RemoteException;

    void match(int p0, IFingerprintServiceCallback p1) throws RemoteException;

    void matchAny(int p0, IFingerprintServiceCallback p1) throws RemoteException;

    void remove(int p0, IFingerprintServiceCallback p1) throws RemoteException;

    void cancel() throws RemoteException;

    String version() throws RemoteException;

    void enableFingerEvent(int p0) throws RemoteException;

    void disableFingerEvent() throws RemoteException;

    void setLivenessEnabled(byte[] p0, boolean p1) throws RemoteException;

    boolean getLivenessEnabled(byte[] p0) throws RemoteException;

    public abstract static class Stub extends Binder implements IFingerprintService {
        private static final String DESCRIPTOR = "com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService";

        private static final int TRANSACTION_enroll = 1;
        private static final int TRANSACTION_match = 2;
        private static final int TRANSACTION_matchAny = 3;
        private static final int TRANSACTION_remove = 4;
        private static final int TRANSACTION_cancel = 5;
        private static final int TRANSACTION_version = 6;
        private static final int TRANSACTION_enableFingerEvent = 7;
        private static final int TRANSACTION_disableFingerEvent = 8;
        private static final int TRANSACTION_setLivenessEnabled = 9;
        private static final int TRANSACTION_getLivenessEnabled = 10;
        private static final int TRANSACTION_writeDescriptor = 1598968902;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IFingerprintService asInterface(IBinder binder) {
            if (binder == null) {
                return null;
            }

            IInterface queryLocalInterface = binder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && queryLocalInterface instanceof IFingerprintService) {
                return (IFingerprintService) queryLocalInterface;
            }

            return new Proxy(binder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            switch (n) {
                case TRANSACTION_enroll:
                    parcel.enforceInterface(DESCRIPTOR);
                    enroll(parcel.readInt(), IFingerprintServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case TRANSACTION_match:
                    parcel.enforceInterface(DESCRIPTOR);
                    match(parcel.readInt(), IFingerprintServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case TRANSACTION_matchAny:
                    parcel.enforceInterface(DESCRIPTOR);
                    matchAny(parcel.readInt(), IFingerprintServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case TRANSACTION_remove:
                    parcel.enforceInterface(DESCRIPTOR);
                    remove(parcel.readInt(), IFingerprintServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case TRANSACTION_cancel:
                    parcel.enforceInterface(DESCRIPTOR);
                    cancel();
                    parcel2.writeNoException();
                    return true;
                case TRANSACTION_version:
                    parcel.enforceInterface(DESCRIPTOR);
                    parcel2.writeNoException();
                    parcel2.writeString(version());
                    return true;
                case TRANSACTION_enableFingerEvent:
                    parcel.enforceInterface(DESCRIPTOR);
                    enableFingerEvent(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case TRANSACTION_disableFingerEvent:
                    parcel.enforceInterface(DESCRIPTOR);
                    disableFingerEvent();
                    parcel2.writeNoException();
                    return true;
                case TRANSACTION_setLivenessEnabled:
                    parcel.enforceInterface(DESCRIPTOR);
                    setLivenessEnabled(parcel.createByteArray(), parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case TRANSACTION_getLivenessEnabled:
                    parcel.enforceInterface(DESCRIPTOR);
                    parcel2.writeNoException();
                    parcel2.writeInt(getLivenessEnabled(parcel.createByteArray()) ? 1 : 0);
                    return true;
                case TRANSACTION_writeDescriptor:
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(n, parcel, parcel2, n2);
            }
        }

        private static class Proxy implements IFingerprintService {
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
            public void enroll(int n, IFingerprintServiceCallback fingerprintServiceCallback) throws RemoteException {
                IBinder binder = null;
                if (fingerprintServiceCallback != null) {
                    binder = fingerprintServiceCallback.asBinder();
                }

                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(n);
                    obtain.writeStrongBinder(binder);

                    mRemote.transact(TRANSACTION_enroll, obtain, obtain2, 0);

                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override
            public void match(int n, IFingerprintServiceCallback fingerprintServiceCallback) throws RemoteException {
                IBinder binder = null;
                if (fingerprintServiceCallback != null) {
                    binder = fingerprintServiceCallback.asBinder();
                }

                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(getInterfaceDescriptor());
                    obtain.writeInt(n);
                    obtain.writeStrongBinder(binder);

                    mRemote.transact(TRANSACTION_match, obtain, obtain2, 0);

                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override
            public void matchAny(int n, IFingerprintServiceCallback fingerprintServiceCallback) throws RemoteException {
                IBinder binder = null;
                if (fingerprintServiceCallback != null) {
                    binder = fingerprintServiceCallback.asBinder();
                }

                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(getInterfaceDescriptor());
                    obtain.writeInt(n);
                    obtain.writeStrongBinder(binder);

                    mRemote.transact(TRANSACTION_matchAny, obtain, obtain2, 0);

                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override
            public void remove(int n, IFingerprintServiceCallback fingerprintServiceCallback) throws RemoteException {
                IBinder binder = null;
                if (fingerprintServiceCallback != null) {
                    binder = fingerprintServiceCallback.asBinder();
                }

                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(getInterfaceDescriptor());
                    obtain.writeInt(n);
                    obtain.writeStrongBinder(binder);

                    mRemote.transact(TRANSACTION_remove, obtain, obtain2, 0);

                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override
            public void cancel() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);

                    mRemote.transact(TRANSACTION_cancel, obtain, obtain2, 0);

                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override
            public String version() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(getInterfaceDescriptor());

                    mRemote.transact(TRANSACTION_version, obtain, obtain2, 0);

                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override
            public void enableFingerEvent(int n) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(n);

                    mRemote.transact(TRANSACTION_enableFingerEvent, obtain, obtain2, 0);

                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override
            public void disableFingerEvent() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);

                    mRemote.transact(TRANSACTION_disableFingerEvent, obtain, obtain2, 0);

                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override
            public void setLivenessEnabled(byte[] array, boolean b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(getInterfaceDescriptor());
                    obtain.writeByteArray(array);
                    obtain.writeInt(b ? 1 : 0);

                    mRemote.transact(TRANSACTION_setLivenessEnabled, obtain, obtain2, 0);

                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override
            public boolean getLivenessEnabled(byte[] array) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(getInterfaceDescriptor());
                    obtain.writeByteArray(array);

                    mRemote.transact(TRANSACTION_getLivenessEnabled, obtain, obtain2, 0);

                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}