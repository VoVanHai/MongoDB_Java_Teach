package mongoconfig;

import java.lang.annotation.ElementType;

import org.hibernate.ogm.cfg.Configurable;
import org.hibernate.ogm.cfg.OptionConfigurator;
import org.hibernate.ogm.datastore.document.options.AssociationStorageType;
import org.hibernate.ogm.datastore.document.options.MapStorageType;
import org.hibernate.ogm.datastore.mongodb.MongoDB;
import org.hibernate.ogm.datastore.mongodb.options.AssociationDocumentStorageType;
import org.hibernate.ogm.datastore.mongodb.options.ReadPreferenceType;
import org.hibernate.ogm.datastore.mongodb.options.WriteConcernType;

import entities.Lophoc;

public class MyOptionConfigurator extends OptionConfigurator {
	@Override
	public void configure(Configurable configurable) {
		configurable. configureOptionsFor(MongoDB. class )
		. writeConcern( WriteConcernType. REPLICA_ACKNOWLEDGED )
		. readPreference( ReadPreferenceType.NEAREST )
		. entity( Lophoc.class )
		. associationStorage( AssociationStorageType. ASSOCIATION_DOCUMENT )
		. associationDocumentStorage( AssociationDocumentStorageType. COLLECTION_PER_ASSOCIATION )
		. mapStorage( MapStorageType.AS_LIST )
		. property( "animals", ElementType.FIELD )
		. associationStorage( AssociationStorageType.IN_ENTITY )
		/*. entity( Animal. class )
		. writeConcern( new RequiringReplicaCountOf( 3 ) )
		. associationStorage( AssociationStorageType. ASSOCIATION_DOCUMENT )*/
		;
	}
}