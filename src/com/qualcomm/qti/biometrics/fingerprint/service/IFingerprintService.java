package com.qualcomm.qti.biometrics.fingerprint.service;

import android.os.*;

public interface IFingerprintService extends IInterface
{
    void cancel() throws RemoteException;
    
    void disableFingerEvent() throws RemoteException;
    
    void enableFingerEvent(final int p0) throws RemoteException;
    
    void enroll(final int p0, final IFingerprintServiceCallback p1) throws RemoteException;
    
    boolean getLivenessEnabled(final byte[] p0) throws RemoteException;
    
    void match(final int p0, final IFingerprintServiceCallback p1) throws RemoteException;
    
    void matchAny(final int p0, final IFingerprintServiceCallback p1) throws RemoteException;
    
    void remove(final int p0, final IFingerprintServiceCallback p1) throws RemoteException;
    
    void setLivenessEnabled(final byte[] p0, final boolean p1) throws RemoteException;
    
    String version() throws RemoteException;
    
    public abstract static class Stub extends Binder implements IFingerprintService
    {
        private static final String DESCRIPTOR = "com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService";
        static final int TRANSACTION_cancel = 5;
        static final int TRANSACTION_disableFingerEvent = 8;
        static final int TRANSACTION_enableFingerEvent = 7;
        static final int TRANSACTION_enroll = 1;
        static final int TRANSACTION_getLivenessEnabled = 10;
        static final int TRANSACTION_match = 2;
        static final int TRANSACTION_matchAny = 3;
        static final int TRANSACTION_remove = 4;
        static final int TRANSACTION_setLivenessEnabled = 9;
        static final int TRANSACTION_version = 6;
        
        public Stub() {
            this.attachInterface((IInterface)this, "com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService");
        }
        
        public static IFingerprintService asInterface(final IBinder binder) {
            if (binder == null) {
                return null;
            }
            final IInterface queryLocalInterface = binder.queryLocalInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService");
            if (queryLocalInterface != null && queryLocalInterface instanceof IFingerprintService) {
                return (IFingerprintService)queryLocalInterface;
            }
            return new Proxy(binder);
        }
        
        public IBinder asBinder() {
            return (IBinder)this;
        }
        
        public boolean onTransact(int n, final Parcel parcel, final Parcel parcel2, final int n2) throws RemoteException {
            final int n3 = 0;
            boolean b = false;
            switch (n) {
                default: {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                case 1598968902: {
                    parcel2.writeString("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService");
                    this.enroll(parcel.readInt(), IFingerprintServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 2: {
                    parcel.enforceInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService");
                    this.match(parcel.readInt(), IFingerprintServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService");
                    this.matchAny(parcel.readInt(), IFingerprintServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 4: {
                    parcel.enforceInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService");
                    this.remove(parcel.readInt(), IFingerprintServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 5: {
                    parcel.enforceInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService");
                    this.cancel();
                    parcel2.writeNoException();
                    return true;
                }
                case 6: {
                    parcel.enforceInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService");
                    final String version = this.version();
                    parcel2.writeNoException();
                    parcel2.writeString(version);
                    return true;
                }
                case 7: {
                    parcel.enforceInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService");
                    this.enableFingerEvent(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                }
                case 8: {
                    parcel.enforceInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService");
                    this.disableFingerEvent();
                    parcel2.writeNoException();
                    return true;
                }
                case 9: {
                    parcel.enforceInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService");
                    final byte[] byteArray = parcel.createByteArray();
                    if (parcel.readInt() != 0) {
                        b = true;
                    }
                    this.setLivenessEnabled(byteArray, b);
                    parcel2.writeNoException();
                    return true;
                }
                case 10: {
                    parcel.enforceInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService");
                    final boolean livenessEnabled = this.getLivenessEnabled(parcel.createByteArray());
                    parcel2.writeNoException();
                    n = n3;
                    if (livenessEnabled) {
                        n = 1;
                    }
                    parcel2.writeInt(n);
                    return true;
                }
            }
        }
        
        private static class Proxy implements IFingerprintService
        {
            private IBinder mRemote;
            
            Proxy(final IBinder mRemote) {
                this.mRemote = mRemote;
            }
            
            public IBinder asBinder() {
                return this.mRemote;
            }
            
            @Override
            public void cancel() throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService");
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                }
                finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
            
            @Override
            public void disableFingerEvent() throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService");
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                }
                finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
            
            @Override
            public void enableFingerEvent(final int n) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService");
                    obtain.writeInt(n);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                }
                finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
            
            @Override
            public void enroll(final int n, final IFingerprintServiceCallback fingerprintServiceCallback) throws RemoteException {
                IBinder binder = null;
                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService");
                    obtain.writeInt(n);
                    if (fingerprintServiceCallback != null) {
                        binder = fingerprintServiceCallback.asBinder();
                    }
                    obtain.writeStrongBinder(binder);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                }
                finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
            
            public String getInterfaceDescriptor() {
                return "com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService";
            }
            
            @Override
            public boolean getLivenessEnabled(final byte[] array) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService");
                    obtain.writeByteArray(array);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                }
                finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
            
            @Override
            public void match(final int n, final IFingerprintServiceCallback fingerprintServiceCallback) throws RemoteException {
                IBinder binder = null;
                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService");
                    obtain.writeInt(n);
                    if (fingerprintServiceCallback != null) {
                        binder = fingerprintServiceCallback.asBinder();
                    }
                    obtain.writeStrongBinder(binder);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                }
                finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
            
            @Override
            public void matchAny(final int n, final IFingerprintServiceCallback fingerprintServiceCallback) throws RemoteException {
                IBinder binder = null;
                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService");
                    obtain.writeInt(n);
                    if (fingerprintServiceCallback != null) {
                        binder = fingerprintServiceCallback.asBinder();
                    }
                    obtain.writeStrongBinder(binder);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                }
                finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
            
            @Override
            public void remove(final int n, final IFingerprintServiceCallback fingerprintServiceCallback) throws RemoteException {
                IBinder binder = null;
                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService");
                    obtain.writeInt(n);
                    if (fingerprintServiceCallback != null) {
                        binder = fingerprintServiceCallback.asBinder();
                    }
                    obtain.writeStrongBinder(binder);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                }
                finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
            
            @Override
            public void setLivenessEnabled(final byte[] array, final boolean b) throws RemoteException {
                int n = 0;
                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService");
                    obtain.writeByteArray(array);
                    if (b) {
                        n = 1;
                    }
                    obtain.writeInt(n);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                }
                finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
            
            @Override
            public String version() throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintService");
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                }
                finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
