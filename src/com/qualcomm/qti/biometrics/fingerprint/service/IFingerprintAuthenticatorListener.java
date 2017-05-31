package com.qualcomm.qti.biometrics.fingerprint.service;

import android.os.*;

public interface IFingerprintAuthenticatorListener extends IInterface
{
    void onCancel(final byte[] p0) throws RemoteException;
    
    void onEnrollmentStatus(final long p0, final boolean p1) throws RemoteException;
    
    void onUserVerificationResult(final byte[] p0, final int p1, final String p2, final long p3, final long p4, final byte[] p5) throws RemoteException;
    
    public abstract static class Stub extends Binder implements IFingerprintAuthenticatorListener
    {
        private static final String DESCRIPTOR = "com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticatorListener";
        static final int TRANSACTION_onCancel = 2;
        static final int TRANSACTION_onEnrollmentStatus = 3;
        static final int TRANSACTION_onUserVerificationResult = 1;
        
        public Stub() {
            this.attachInterface((IInterface)this, "com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticatorListener");
        }
        
        public static IFingerprintAuthenticatorListener asInterface(final IBinder binder) {
            if (binder == null) {
                return null;
            }
            final IInterface queryLocalInterface = binder.queryLocalInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticatorListener");
            if (queryLocalInterface != null && queryLocalInterface instanceof IFingerprintAuthenticatorListener) {
                return (IFingerprintAuthenticatorListener)queryLocalInterface;
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
                    parcel2.writeString("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticatorListener");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticatorListener");
                    this.onUserVerificationResult(parcel.createByteArray(), parcel.readInt(), parcel.readString(), parcel.readLong(), parcel.readLong(), parcel.createByteArray());
                    return true;
                }
                case 2: {
                    parcel.enforceInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticatorListener");
                    this.onCancel(parcel.createByteArray());
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticatorListener");
                    this.onEnrollmentStatus(parcel.readLong(), parcel.readInt() != 0);
                    return true;
                }
            }
        }
        
        private static class Proxy implements IFingerprintAuthenticatorListener
        {
            private IBinder mRemote;
            
            Proxy(final IBinder mRemote) {
                this.mRemote = mRemote;
            }
            
            public IBinder asBinder() {
                return this.mRemote;
            }
            
            public String getInterfaceDescriptor() {
                return "com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticatorListener";
            }
            
            @Override
            public void onCancel(final byte[] array) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticatorListener");
                    obtain.writeByteArray(array);
                    this.mRemote.transact(2, obtain, (Parcel)null, 1);
                }
                finally {
                    obtain.recycle();
                }
            }
            
            @Override
            public void onEnrollmentStatus(final long n, final boolean b) throws RemoteException {
                int n2 = 1;
                final Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticatorListener");
                    obtain.writeLong(n);
                    if (!b) {
                        n2 = 0;
                    }
                    obtain.writeInt(n2);
                    this.mRemote.transact(3, obtain, (Parcel)null, 1);
                }
                finally {
                    obtain.recycle();
                }
            }
            
            @Override
            public void onUserVerificationResult(final byte[] array, final int n, final String s, final long n2, final long n3, final byte[] array2) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintAuthenticatorListener");
                    obtain.writeByteArray(array);
                    obtain.writeInt(n);
                    obtain.writeString(s);
                    obtain.writeLong(n2);
                    obtain.writeLong(n3);
                    obtain.writeByteArray(array2);
                    this.mRemote.transact(1, obtain, (Parcel)null, 1);
                }
                finally {
                    obtain.recycle();
                }
            }
        }
    }
}
