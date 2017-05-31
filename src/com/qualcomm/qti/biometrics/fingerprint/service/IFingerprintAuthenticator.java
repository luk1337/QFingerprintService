package com.qualcomm.qti.biometrics.fingerprint.service;

import android.os.*;

public interface IFingerprintAuthenticator extends IInterface
{
    void cancel(final byte[] p0, final IFingerprintAuthenticatorListener p1) throws RemoteException;
    
    void getEnrollmentStatus(final long p0, final IFingerprintAuthenticatorListener p1) throws RemoteException;
    
    void verifyUser(final byte[] p0, final String p1, final IFingerprintAuthenticatorListener p2) throws RemoteException;
    
    void verifyUserExt(final byte[] p0, final String p1, final int p2, final IFingerprintAuthenticatorListener p3) throws RemoteException;
    
    public abstract static class Stub extends Binder implements IFingerprintAuthenticator
    {
        private static final String DESCRIPTOR = "com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticator";
        static final int TRANSACTION_cancel = 2;
        static final int TRANSACTION_getEnrollmentStatus = 3;
        static final int TRANSACTION_verifyUser = 1;
        static final int TRANSACTION_verifyUserExt = 4;
        
        public Stub() {
            this.attachInterface((IInterface)this, "com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticator");
        }
        
        public static IFingerprintAuthenticator asInterface(final IBinder binder) {
            if (binder == null) {
                return null;
            }
            final IInterface queryLocalInterface = binder.queryLocalInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticator");
            if (queryLocalInterface != null && queryLocalInterface instanceof IFingerprintAuthenticator) {
                return (IFingerprintAuthenticator)queryLocalInterface;
            }
            return new Proxy(binder);
        }
        
        public IBinder asBinder() {
            return (IBinder)this;
        }
        
        public boolean onTransact(final int n, final Parcel parcel, final Parcel parcel2, final int n2) throws RemoteException {
            switch (n) {
                default: {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                case 1598968902: {
                    parcel2.writeString("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticator");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticator");
                    this.verifyUser(parcel.createByteArray(), parcel.readString(), IFingerprintAuthenticatorListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 2: {
                    parcel.enforceInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticator");
                    this.cancel(parcel.createByteArray(), IFingerprintAuthenticatorListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticator");
                    this.getEnrollmentStatus(parcel.readLong(), IFingerprintAuthenticatorListener.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                }
                case 4: {
                    parcel.enforceInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticator");
                    this.verifyUserExt(parcel.createByteArray(), parcel.readString(), parcel.readInt(), IFingerprintAuthenticatorListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
            }
        }
        
        private static class Proxy implements IFingerprintAuthenticator
        {
            private IBinder mRemote;
            
            Proxy(final IBinder mRemote) {
                this.mRemote = mRemote;
            }
            
            public IBinder asBinder() {
                return this.mRemote;
            }
            
            @Override
            public void cancel(final byte[] array, final IFingerprintAuthenticatorListener fingerprintAuthenticatorListener) throws RemoteException {
                final IBinder binder = null;
                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticator");
                    obtain.writeByteArray(array);
                    IBinder binder2 = binder;
                    if (fingerprintAuthenticatorListener != null) {
                        binder2 = fingerprintAuthenticatorListener.asBinder();
                    }
                    obtain.writeStrongBinder(binder2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                }
                finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
            
            @Override
            public void getEnrollmentStatus(final long n, final IFingerprintAuthenticatorListener fingerprintAuthenticatorListener) throws RemoteException {
                IBinder binder = null;
                final Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticator");
                    obtain.writeLong(n);
                    if (fingerprintAuthenticatorListener != null) {
                        binder = fingerprintAuthenticatorListener.asBinder();
                    }
                    obtain.writeStrongBinder(binder);
                    this.mRemote.transact(3, obtain, (Parcel)null, 1);
                }
                finally {
                    obtain.recycle();
                }
            }
            
            public String getInterfaceDescriptor() {
                return "com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticator";
            }
            
            @Override
            public void verifyUser(final byte[] array, final String s, final IFingerprintAuthenticatorListener fingerprintAuthenticatorListener) throws RemoteException {
                final IBinder binder = null;
                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticator");
                    obtain.writeByteArray(array);
                    obtain.writeString(s);
                    IBinder binder2 = binder;
                    if (fingerprintAuthenticatorListener != null) {
                        binder2 = fingerprintAuthenticatorListener.asBinder();
                    }
                    obtain.writeStrongBinder(binder2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                }
                finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
            
            @Override
            public void verifyUserExt(final byte[] array, final String s, final int n, final IFingerprintAuthenticatorListener fingerprintAuthenticatorListener) throws RemoteException {
                final IBinder binder = null;
                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticator");
                    obtain.writeByteArray(array);
                    obtain.writeString(s);
                    obtain.writeInt(n);
                    IBinder binder2 = binder;
                    if (fingerprintAuthenticatorListener != null) {
                        binder2 = fingerprintAuthenticatorListener.asBinder();
                    }
                    obtain.writeStrongBinder(binder2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                }
                finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
