package com.qualcomm.qti.biometrics.fingerprint.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IFingerprintAuthenticator extends IInterface {
    void verifyUser(byte[] p0, String p1, IFingerprintAuthenticatorListener p2) throws RemoteException;

    void cancel(byte[] p0, IFingerprintAuthenticatorListener p1) throws RemoteException;

    void getEnrollmentStatus(long p0, IFingerprintAuthenticatorListener p1) throws RemoteException;

    void verifyUserExt(byte[] p0, String p1, int p2, IFingerprintAuthenticatorListener p3) throws RemoteException;

    public abstract static class Stub extends Binder implements IFingerprintAuthenticator {
        private static final String DESCRIPTOR = "com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticator";

        private static final int TRANSACTION_verifyUser = 1;
        private static final int TRANSACTION_cancel = 2;
        private static final int TRANSACTION_getEnrollmentStatus = 3;
        private static final int TRANSACTION_verifyUserExt = 4;
        private static final int TRANSACTION_writeDescriptor = 1598968902;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IFingerprintAuthenticator asInterface(final IBinder binder) {
            if (binder == null) {
                return null;
            }

            final IInterface queryLocalInterface = binder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && queryLocalInterface instanceof IFingerprintAuthenticator) {
                return (IFingerprintAuthenticator) queryLocalInterface;
            }

            return new Proxy(binder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            switch (n) {
                case TRANSACTION_verifyUser:
                    parcel.enforceInterface(DESCRIPTOR);
                    verifyUser(parcel.createByteArray(), parcel.readString(), IFingerprintAuthenticatorListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case TRANSACTION_cancel:
                    parcel.enforceInterface(DESCRIPTOR);
                    cancel(parcel.createByteArray(), IFingerprintAuthenticatorListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case TRANSACTION_getEnrollmentStatus:
                    parcel.enforceInterface(DESCRIPTOR);
                    getEnrollmentStatus(parcel.readLong(), IFingerprintAuthenticatorListener.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case TRANSACTION_verifyUserExt:
                    parcel.enforceInterface(DESCRIPTOR);
                    verifyUserExt(parcel.createByteArray(), parcel.readString(), parcel.readInt(), IFingerprintAuthenticatorListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case TRANSACTION_writeDescriptor:
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(n, parcel, parcel2, n2);
            }
        }

        private static class Proxy implements IFingerprintAuthenticator {
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
            public void verifyUser(byte[] array, String s, IFingerprintAuthenticatorListener fingerprintAuthenticatorListener) throws RemoteException {
                IBinder binder = null;
                if (fingerprintAuthenticatorListener != null) {
                    binder = fingerprintAuthenticatorListener.asBinder();
                }

                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(getInterfaceDescriptor());
                    obtain.writeByteArray(array);
                    obtain.writeString(s);
                    obtain.writeStrongBinder(binder);

                    mRemote.transact(TRANSACTION_verifyUser, obtain, obtain2, 0);

                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override
            public void cancel(byte[] array, IFingerprintAuthenticatorListener fingerprintAuthenticatorListener) throws RemoteException {
                IBinder binder = null;
                if (fingerprintAuthenticatorListener != null) {
                    binder = fingerprintAuthenticatorListener.asBinder();
                }

                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(getInterfaceDescriptor());
                    obtain.writeByteArray(array);
                    obtain.writeStrongBinder(binder);

                    mRemote.transact(TRANSACTION_cancel, obtain, obtain2, 0);

                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override
            public void getEnrollmentStatus(long n, IFingerprintAuthenticatorListener fingerprintAuthenticatorListener) throws RemoteException {
                IBinder binder = null;
                if (fingerprintAuthenticatorListener != null) {
                    binder = fingerprintAuthenticatorListener.asBinder();
                }

                final Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(getInterfaceDescriptor());
                    obtain.writeLong(n);
                    obtain.writeStrongBinder(binder);

                    mRemote.transact(TRANSACTION_getEnrollmentStatus, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override
            public void verifyUserExt(byte[] array, String s, int n, IFingerprintAuthenticatorListener fingerprintAuthenticatorListener) throws RemoteException {
                IBinder binder = null;
                if (fingerprintAuthenticatorListener != null) {
                    binder = fingerprintAuthenticatorListener.asBinder();
                }

                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(getInterfaceDescriptor());
                    obtain.writeByteArray(array);
                    obtain.writeString(s);
                    obtain.writeInt(n);
                    obtain.writeStrongBinder(binder);

                    mRemote.transact(TRANSACTION_verifyUserExt, obtain, obtain2, 0);

                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}