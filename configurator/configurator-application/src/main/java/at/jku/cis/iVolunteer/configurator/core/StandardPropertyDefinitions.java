package at.jku.cis.iVolunteer.configurator.core;


import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.configurator.meta.core.property.definition.flatProperty.FlatPropertyDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinitionTypes.BooleanPropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinitionTypes.DatePropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinitionTypes.DoublePropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinitionTypes.LocationPropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinitionTypes.LongPropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinitionTypes.LongTextPropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinitionTypes.TextPropertyDefinition;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Component
public class StandardPropertyDefinitions {

	@Autowired public FlatPropertyDefinitionRepository propertyDefinitionRepository;

	public StandardPropertyDefinitions() {

	}
	public List<FlatPropertyDefinition<Object>> getAlliVolunteer() {
		List<FlatPropertyDefinition<Object>> properties = getAllHeader();
		properties.addAll(getAllGeneric());
		properties.add(new FlatPropertyDefinition<Object>("IVolunteerUUID", PropertyType.TEXT));
		properties.add(new FlatPropertyDefinition<Object>("IVolunteerSource", PropertyType.TEXT));
		properties.add(new FlatPropertyDefinition<Object>("IssuedOn", PropertyType.DATE));
		properties.add(new FlatPropertyDefinition<Object>("Icon", PropertyType.TEXT));
		properties.add(new FlatPropertyDefinition<Object>("Purpose", PropertyType.TEXT));
		properties.add(new FlatPropertyDefinition<Object>("Phase", PropertyType.TEXT));
		properties.add(new FlatPropertyDefinition<Object>("Unit", PropertyType.TEXT));
		properties.add(new FlatPropertyDefinition<Object>("Level", PropertyType.TEXT));
		properties.add(new FlatPropertyDefinition<Object>("GeoInformation", PropertyType.TEXT));
		properties.add(new FlatPropertyDefinition<Object>("RoleID", PropertyType.TEXT));
		properties.add(new FlatPropertyDefinition<Object>("OrganisationID", PropertyType.TEXT));
		properties.add(new FlatPropertyDefinition<Object>("OrganisationName", PropertyType.TEXT));
		properties.add(new FlatPropertyDefinition<Object>("OrganisationType", PropertyType.TEXT));
		properties.add(new FlatPropertyDefinition<Object>("BadgeID", PropertyType.TEXT));
		properties.add(new FlatPropertyDefinition<Object>("CertificateID", PropertyType.TEXT));
		properties.add(new FlatPropertyDefinition<Object>("TaskID", PropertyType.TEXT));
		
		return properties;
	}

	public List<FlatPropertyDefinition<Object>> getAllHeader() {
		List<FlatPropertyDefinition<?>> props = new LinkedList<>();

		props.add(new IDProperty());
		props.add(new EvidenzProperty());
		props.add(new DateFromProperty());
		props.add(new DateToProperty());
		props.add(new ImageLinkProperty());
		props.add(new ExpiredProperty());
		props.add(new TaskType1Property());
		props.add(new TaskType2Property());
		props.add(new TaskType3Property());
		props.add(new TaskType4Property());
		props.add(new RankProperty());
		props.add(new DurationProperty());

		return new ArrayList(props);

	}

	public List<FlatPropertyDefinition<Object>> getAllGeneric() {
		List<FlatPropertyDefinition<?>> props = new LinkedList<>();

		props.add(new NameProperty());
		props.add(new DescriptionProperty());
		props.add(new WorkflowKeyProperty());
		props.add(new ContentProperty());
		props.add(new PriorityProperty());
		props.add(new ImportancyProperty());
		props.add(new RoleProperty());
		props.add(new LocationProperty());
		props.add(new RequiredEquipmentProperty());
		props.add(new WorkshiftProperty());
		props.add(new TaskPeriodTypeProperty());
		props.add(new KeywordsProperty());
		props.add(new RewardsProperty());
		props.add(new PostcodeProperty());
		props.add(new NumberOfVolunteersProperty());
		props.add(new TaskPeriodValueProperty());
		props.add(new StartDateProperty());
		props.add(new EndDateProperty());
		props.add(new UrgentProperty());
		props.add(new HighlightedProperty());
		props.add(new PromotedProperty());
		props.add(new FeedbackRequestedProperty());
		props.add(new RemindParticipantsProperty());
		props.add(new LatitudeProperty());
		props.add(new LongitudeProperty());
		props.add(new VolunteerAgeProperty());

		return new ArrayList(props);

	}

	public List<FlatPropertyDefinition<Object>> getAllFlexProdProperties() {
		List<FlatPropertyDefinition<?>> list = new LinkedList<>();

		list.add(new MaxGluehtemperaturProperty());
		list.add(new VerfuegbaresSchutzgasProperty());
		list.add(new BauartProperty());
		list.add(new TemperaturhomogenitaetProperty());
		list.add(new KaltgewalztesMaterialZulaessigProperty());
		list.add(new WarmgewalztesMaterialZulaessigProperty());

		list.add(new BundEntfettenProperty());

		list.add(new InnendurchmesserProperty());
		list.add(new AussendurchmesserProperty());
		list.add(new HoeheProperty());

		list.add(new GluehzeitProperty());
		list.add(new DurchmesserProperty());
		list.add(new DurchsatzProperty());
		
		list.add(new ChargierhilfeProperty());
		list.add(new WalzartProperty());

		list.add(new MoeglicheInnendurchmesserProperty());
		list.add(new MaxAussendurchmesserProperty());
		list.add(new MaxChargierhoeheProperty());

		list.add(new CQI9Property());
		list.add(new TUSProperty());

		list.add(new LetzteWartungProperty());
		list.add(new WartungsintervallProperty());

		list.add(new BandBreiteProperty());
		list.add(new BandstaerkeProperty());

		list.add(new WarmgewalztProperty());
		list.add(new KaltgewalztProperty());

		list.add(new StreckgrenzeProperty());
		list.add(new ZugfestigkeitProperty());
		list.add(new DehnungProperty());

		list.add(new GefuegeProperty());

		list.add(new MaterialBereitgestelltProperty());
		list.add(new LieferortProperty());
		list.add(new VerpackungProperty());
		list.add(new TransportartProperty());
		list.add(new MengeProperty());
		list.add(new LieferdatumProperty());
		list.add(new IncotermsProperty());

		list.add(new ZahlungsbedingungenProperty());

		list.add(new TitelProperty());
		list.add(new ProdukttypProperty());
		list.add(new MengeProperty());
		list.add(new MinimaleMengeProperty());
		list.add(new LieferdatumProperty());
		list.add(new WerkstoffBereitgestelltProperty());
		list.add(new BeschreibungZusatzinfoProperty());
		
		list.add(new DurchmesserInnenProperty());
		list.add(new DurchmesserAussenProperty());
		list.add(new HoeheProperty());
		
		list.add(new WerkstoffProperty());
		list.add(new WerkstoffFreitextProperty());
		list.add(new ZugfestigkeitProperty());
		
		list.add(new SchutzgasProperty());
		list.add(new GluehreiseProperty());
		list.add(new TemperaturhomogenitaetProperty());
		
		list.add(new OberflaechenqualitaetProperty());
		list.add(new ZusaetzlicheProduktinformationenProperty());
		
		list.add(new IncotermsProperty());
		list.add(new LieferortProperty());
		list.add(new AbholortProperty());
		list.add(new VerpackungsvorgabenProperty());
		
		list.add(new BandDickeProperty());
		
		list.add(new DurchmesserKronenstockProperty());
		list.add(new MaximaldurchmesserBundProperty());
		
		list.add(new DurchmesserDornProperty());
		list.add(new InnendurchmesserOfenProperty());
		
		list.add(new OfenHoeheProperty());
		list.add(new MaxGluehtemperaturProperty());
		list.add(new TemperaturhomogenitaetProperty());
		list.add(new ErforderlicheTemperaturhomogenitaetProperty());
		list.add(new AufheizrateProperty());
		list.add(new AbkuehlrateProperty());
		list.add(new MaxAnteilH2Property());
		list.add(new KapazitaetProperty());
		list.add(new GluehprogrammVerfuegbarProperty());
		
		
		return new ArrayList(list);
	}

	/**
	 * 
	 * Header Properties
	 *
	 */
	public static class IDProperty extends TextPropertyDefinition {

		IDProperty() {
			inst();
		}

		@PostConstruct
		public void inst() {
			this.setName("ID");
		}
	}

	public static class EvidenzProperty extends TextPropertyDefinition {

		EvidenzProperty() {
			inst();
		}

		@PostConstruct
		public void inst() {
			this.setName("Evidenz");
		}
	}

	
	public static class ImageLinkProperty extends TextPropertyDefinition {

		ImageLinkProperty() {
			inst();
		}

		@PostConstruct
		public void inst() {
			this.setName("Image Link");
		}
	}
	
	public static class ExpiredProperty extends BooleanPropertyDefinition {

		ExpiredProperty() {
			inst();
		}

		@PostConstruct
		public void inst() {
			this.setName("Expired");
		}
	}
	
	public static class LocationProperty extends LocationPropertyDefinition {

		LocationProperty() {
			inst();
		}

		@PostConstruct
		public void inst() {
			this.setName("Location");
		}
	}
	
	
	public static class DateFromProperty extends DatePropertyDefinition {

		DateFromProperty() {
			inst();
		}

		@PostConstruct
		public void inst() {
			this.setName("Starting Date");
		}
	}

	public static class DateToProperty extends DatePropertyDefinition {

		DateToProperty() {
			inst();
		}

		@PostConstruct
		public void inst() {
			this.setName("End Date");
		}
	}

	public static class TaskType1Property extends TextPropertyDefinition {

		TaskType1Property() {
			inst();
		}

		@PostConstruct
		public void inst() {
			this.setName("TaskType1");
		}
	}

	public static class TaskType2Property extends TextPropertyDefinition {

		TaskType2Property() {
			inst();
		}

		@PostConstruct
		public void inst() {
			this.setName("TaskType2");
		}
	}

	public static class TaskType3Property extends TextPropertyDefinition {

		TaskType3Property() {
			inst();
		}

		@PostConstruct
		public void inst() {
			this.setName("TaskType3");
		}
	}

	public static class TaskType4Property extends TextPropertyDefinition {

		TaskType4Property() {
			inst();
		}

		@PostConstruct
		public void inst() {
			this.setName("TaskType4");
		}
	}

	public static class RankProperty extends TextPropertyDefinition {

		RankProperty() {
			inst();
		}

		@PostConstruct
		public void inst() {
			this.setName("Rank");
		}
	}

	public static class DurationProperty extends LongPropertyDefinition {

		DurationProperty() {
			inst();
		}

		@PostConstruct
		public void inst() {
			this.setName("Duration");
		}
	}

	/**
	 * 
	 * Standard Properties
	 *
	 */
	
	// =========================================
	// ========== Text Properties ==============
	// =========================================
	public static class NameProperty extends TextPropertyDefinition {

		NameProperty() {
			inst();
		}

		@PostConstruct
		public void inst() {
			this.setType(PropertyType.TEXT);
			this.setName("Name");
			this.setRequired(false);

//			List<PropertyConstraint<?>> constraints = new ArrayList<>();
//			constraints.add(new MinimumTextLength(3));
//			constraints.add(new MaximumTextLength(10));
//			constraints.add(new TextPattern("^[A-Za-z][A-Za-zöäüÖÄÜß\\s]*"));
//			this.setPropertyConstraints(new ArrayList(constraints));

		}
	}

	public static class DescriptionProperty extends LongTextPropertyDefinition {

		public DescriptionProperty() {
			inst();
		}

		@PostConstruct
		public void inst() {
			this.setName("Description");
			this.setRequired(true);
		}
	}

	public static class WorkflowKeyProperty extends TextPropertyDefinition {
		public WorkflowKeyProperty() {
			inst();
		}

		@PostConstruct
		public void inst() {
			this.setName("Workflow Key");
		}
	}

	public static class ContentProperty extends TextPropertyDefinition {
		public ContentProperty() {
			inst();
		}

		public void inst() {
			this.setName("Content");
		}
	}

	public static class PriorityProperty extends TextPropertyDefinition {
		public PriorityProperty() {
			inst();
		}

		public void inst() {
			this.setName("Priority");

			List<String> legalValues = new ArrayList<>();
			legalValues.add("Low");
			legalValues.add("Normal");
			legalValues.add("High");
			legalValues.add("Critical");
			this.setAllowedValues(legalValues);
		}
	}

	public static class ImportancyProperty extends TextPropertyDefinition {
		public ImportancyProperty() {
			inst();
		}

		public void inst() {
			this.setName("Importancy");

			List<String> legalValues = new ArrayList<>();
			legalValues.add("Not Important");
			legalValues.add("Somewhat Important");
			legalValues.add("Important");
			legalValues.add("Very Important");
			legalValues.add("Critically Important");
			this.setAllowedValues(legalValues);

		}
	}

	public static class RoleProperty extends TextPropertyDefinition {
		public RoleProperty() {
			inst();
		}

		public void inst() {
			this.setName("Role");
		}
	}

//	public static class LocationProperty extends TextPropertyDefinition {
//		public LocationProperty() {
//			inst();
//		}
//
//		public void inst() {
//			this.setName("Location");
//		
//		}
//	}

	public static class RequiredEquipmentProperty extends TextPropertyDefinition {
		public RequiredEquipmentProperty() {
			inst();
		}

		public void inst() {
			this.setName("Required Equipment");
		}
	}

	public static class WorkshiftProperty extends TextPropertyDefinition {
		public WorkshiftProperty() {
			inst();
		}

		public void inst() {
			this.setName("Allocated Shift");

			List<String> legalValues = new ArrayList<>();
			legalValues.add("Morning");
			legalValues.add("Day");
			legalValues.add("Evening");
			legalValues.add("Evening-Night");
			legalValues.add("Night");
			legalValues.add("Night-Morning");
			this.setAllowedValues(legalValues);
		}
	}

	public static class TaskPeriodTypeProperty extends TextPropertyDefinition {
		public TaskPeriodTypeProperty() {
			inst();
		}

		public void inst() {
			this.setName("Period Type");
		

			List<String> legalValues = new ArrayList<>();
			legalValues.add("Days");
			legalValues.add("Weeks");
			legalValues.add("Months");
			legalValues.add("Weekly");
			legalValues.add("Daily");
			legalValues.add("Monthly");
			this.setAllowedValues(legalValues);

		}
	}

	public static class KeywordsProperty extends TextPropertyDefinition {
		public KeywordsProperty() {
			inst();
		}

		public void inst() {
			this.setName("Keywords");
		
		}

	}

	//// Rewards ??
	public static class RewardsProperty extends TextPropertyDefinition {
		public RewardsProperty() {
			inst();
		}

		public void inst() {
			this.setName("Offered Reward(s)");
		
		}
	}

	// =========================================
	// ========== Number Properties ============
	// =========================================

	public static class PostcodeProperty extends LongPropertyDefinition {
		public PostcodeProperty() {
			inst();
		}

		public void inst() {
			this.setName("Postcode");
		

		}
	}

	public static class NumberOfVolunteersProperty extends LongPropertyDefinition {
		public NumberOfVolunteersProperty() {
			inst();
		}

		public void inst() {
			this.setName("Number of Volunteers");
		

			List<Long> legalValues = new LinkedList<>();
			for (long i = 1; i <= 10; i++) {
				legalValues.add(i);
			}
			this.setAllowedValues(legalValues);
		}
	}

	public static class TaskPeriodValueProperty extends LongPropertyDefinition {
		public TaskPeriodValueProperty() {
			inst();
		}

		public void inst() {
			this.setName("Period length");
		

			List<Long> defaultValues = new ArrayList<>();
			defaultValues.add(1L);
			this.setAllowedValues(defaultValues);
		}
	}

	public static class VolunteerAgeProperty extends LongPropertyDefinition {
		public VolunteerAgeProperty() {
			inst();
		}

		public void inst() {
			this.setName("Alter");
		
		}
	}

	// =========================================
	// ========== Date Properties ==============
	// =========================================

	public static class StartDateProperty extends DatePropertyDefinition {
		public StartDateProperty() {
			super();
			this.setName("Starting Date");
		
			setTestValues();
		}

		public void setTestValues() {

			List<Date> defaultValues = new ArrayList<>();
			defaultValues.add(new Date());
			this.setAllowedValues(defaultValues);

		}
	}

	public static class EndDateProperty extends DatePropertyDefinition {
		public EndDateProperty() {
			this.setName("End Date");
		
			setTestValues();
		}

		public void setTestValues() {

		}
	}

	// =========================================
	// ========== Bool Properties ==============
	// =========================================

	public static class UrgentProperty extends BooleanPropertyDefinition {
		public UrgentProperty() {
			inst();
		}

		public void inst() {
			this.setName("Urgent");
		
		}
	}

	public static class HighlightedProperty extends BooleanPropertyDefinition {
		public HighlightedProperty() {
			inst();
		}

		public void inst() {
			this.setName("Highlighted");
		
		}
	}

	public static class PromotedProperty extends BooleanPropertyDefinition {
		public PromotedProperty() {
			inst();
		}

		public void inst() {
			this.setName("Promotion");
		
		}
	}

	public static class FeedbackRequestedProperty extends BooleanPropertyDefinition {
		public FeedbackRequestedProperty() {
			inst();
		}

		public void inst() {
			this.setName("Feedback Requested");
		
		}
	}

	public static class RemindParticipantsProperty extends BooleanPropertyDefinition {
		public RemindParticipantsProperty() {
			inst();
		}

		public void inst() {
			this.setName("Remind Participants");
		
		}
	}

	// =========================================
	// ==== Floating Point Number Properties ===
	// =========================================

	public static class LatitudeProperty extends DoublePropertyDefinition {
		public LatitudeProperty() {
			inst();
			setTestValues();
		}

		public void inst() {
			this.setName("Latitude");
		
		}

		private void setTestValues() {

		}
	}

	public static class LongitudeProperty extends DoublePropertyDefinition {
		public LongitudeProperty() {
			inst();
			setTestValues();
		}

		public void inst() {
			this.setName("Longitude");
		
		}

		private void setTestValues() {

		}
	}

	// -----------------------------------------
	// --------------FlexProd Properties
	// -----------------------------------------

	public static class VerfuegbaresSchutzgasProperty extends TextPropertyDefinition {
		public VerfuegbaresSchutzgasProperty() {
		
			this.setId("verfuegbaresschutzgas");
			this.setName("Verfügbares Schutzgas");
			this.setAllowedValues(new LinkedList<String>());
			this.getAllowedValues().add("H2");
			this.getAllowedValues().add("N2");
			this.getAllowedValues().add("75% N2");
		}
	}

	public static class BauartProperty extends TextPropertyDefinition {
		public BauartProperty() {
		
			this.setId("bauart");
			this.setName("Bauart");
			this.setAllowedValues(new LinkedList<String>());
			this.getAllowedValues().add("Band");
			this.getAllowedValues().add("Draht");
		}
	}

	public static class KaltgewalztesMaterialZulaessigProperty extends BooleanPropertyDefinition {
		public KaltgewalztesMaterialZulaessigProperty() {
		
			this.setId("kaltgewalztesmaterialzulaessig");
			this.setName("Kaltgewalztes Material zulässig");
		}
	}

	public static class WarmgewalztesMaterialZulaessigProperty extends BooleanPropertyDefinition {
		public WarmgewalztesMaterialZulaessigProperty() {
		
			this.setId("warmgewalztesmaterialzulaessig");
			this.setName("Warmgewalztes Material zulässig");
		}
	}

	public static class BundEntfettenProperty extends BooleanPropertyDefinition {
		public BundEntfettenProperty() {
		
			this.setId("bundentfetten");
			this.setName("Bund Entfetten");
		}
	}

	public static class ChargierhilfeProperty extends TextPropertyDefinition {
		public ChargierhilfeProperty() {
		
			this.setId("chargierhilfe");
			this.setName("Chargierhilfe");
			this.setAllowedValues(new LinkedList<String>());
			this.getAllowedValues().add("Konvektoren");
			this.getAllowedValues().add("Tragerahmen");
			this.getAllowedValues().add("Zwischenrahmen");
			this.getAllowedValues().add("Kronenstöcke");
			this.getAllowedValues().add("Chargierkörbe");
		}
	}

	public static class InnendurchmesserProperty extends LongPropertyDefinition {
		public InnendurchmesserProperty() {
		
			this.setId("innendurchmesser");
			this.setName("Innendurchmesser");
		}
	}

	public static class AussendurchmesserProperty extends LongPropertyDefinition {
		public AussendurchmesserProperty() {
		
			this.setId("aussendurchmesser");
			this.setName("Außendurchmesser");
		}
	}

	public static class GluehzeitProperty extends LongPropertyDefinition {
		public GluehzeitProperty() {
		
			this.setId("gluehzeit");
			this.setName("Glühzeit");
		}
	}

	public static class DurchsatzProperty extends LongPropertyDefinition {
		public DurchsatzProperty() {
		
			this.setId("durchsatz");
			this.setName("Durchsatz");
		}
	}

	public static class MoeglicheInnendurchmesserProperty extends LongPropertyDefinition {
		public MoeglicheInnendurchmesserProperty() {
		
			this.setId("moeglicheinnendurchmesser");
			this.setName("Mögliche Innendurchmesser");
			this.setMultiple(true);
		}
	}

	public static class MaxAussendurchmesserProperty extends LongPropertyDefinition {
		public MaxAussendurchmesserProperty() {
		
			this.setId("maxaussendurchmesser");
			this.setName("Max. Außendurchmesser");
		}
	}

	public static class MaxChargierhoeheProperty extends LongPropertyDefinition {
		public MaxChargierhoeheProperty() {
		
			this.setId("maxchargierhoehe");
			this.setName("Max. Chargierhöhe");
		}
	}

	public static class CQI9Property extends BooleanPropertyDefinition {
		public CQI9Property() {
		
			this.setId("cqi9");
			this.setName("CQI-9");
		}
	}

	public static class TUSProperty extends BooleanPropertyDefinition {
		public TUSProperty() {
		
			this.setId("tus");
			this.setName("TUS");
		}
	}

	public static class LetzteWartungProperty extends DatePropertyDefinition {
		public LetzteWartungProperty() {
		
			this.setId("letztewartung");
			this.setName("Letzte Wartung");
		}
	}

	public static class WartungsintervallProperty extends DatePropertyDefinition {
		public WartungsintervallProperty() {
		
			this.setId("wartungsintervall");
			this.setName("Wartungsintervall");
		}
	}

	public static class BandstaerkeProperty extends BooleanPropertyDefinition {
		public BandstaerkeProperty() {
		
			this.setId("bandstaerke");
			this.setName("Bandstärke");
		}
	}

	public static class WarmgewalztProperty extends BooleanPropertyDefinition {
		public WarmgewalztProperty() {
		
			this.setId("warmgewalzt");
			this.setName("Warmgewalzt");
		}
	}

	public static class KaltgewalztProperty extends BooleanPropertyDefinition {
		public KaltgewalztProperty() {
		
			this.setId("kaltgewalzt");
			this.setName("Kaltgewalzt");
		}
	}

	public static class WalzartProperty extends TextPropertyDefinition {
		public WalzartProperty() {
		
			this.setId("walzart");
			this.setName("Walzart");
			this.setAllowedValues(new LinkedList<String>());
			this.getAllowedValues().add("Warmgewalzt");
			this.getAllowedValues().add("Kaltgewalzt");
		}
	}

	public static class StreckgrenzeProperty extends LongPropertyDefinition {
		public StreckgrenzeProperty() {
		
			this.setId("streckgrenze");
			this.setName("Streckgrenze");
		}
	}

	public static class DehnungProperty extends LongPropertyDefinition {
		public DehnungProperty() {
		
			this.setId("dehnung");
			this.setName("Dehnung");
		}
	}

	public static class GefuegeProperty extends TextPropertyDefinition {
		public GefuegeProperty() {
		
			this.setId("gefuege");
			this.setName("Gefüge");
		}
	}

	public static class MaterialBereitgestelltProperty extends BooleanPropertyDefinition {
		public MaterialBereitgestelltProperty() {
		
			this.setId("materialbereitgestellt");
			this.setName("Material bereitgestellt?");
		}
	}

	public static class VerpackungProperty extends TextPropertyDefinition {
		public VerpackungProperty() {
		
			this.setId("verpackung");
			this.setName("Verpackung");
		}
	}

	public static class TransportartProperty extends TextPropertyDefinition {
		public TransportartProperty() {
		
			this.setId("transportart");
			this.setName("Transportart");
			this.setAllowedValues(new LinkedList<String>());
			this.getAllowedValues().add("LKW");
			this.getAllowedValues().add("Zug");
			this.getAllowedValues().add("Schiff");
			this.getAllowedValues().add("Sonstiges");
		}
	}

	public static class ZahlungsbedingungenProperty extends LongTextPropertyDefinition {
		public ZahlungsbedingungenProperty() {
		
			this.setId("zahlungsbedingungen");
			this.setName("Zahlungsbedingungen");
		}
	}

	public static class TitelProperty extends TextPropertyDefinition {
		TitelProperty() {
		
			this.setId("titel");
			this.setName("Titel");
		}
	}

	public static class ProdukttypProperty extends TextPropertyDefinition {
		ProdukttypProperty() {
		
			this.setId("produkttyp");
			this.setName("Produkttyp");

			this.setAllowedValues(new ArrayList<String>());
			this.getAllowedValues().add("Band");
			this.getAllowedValues().add("Draht");
			this.getAllowedValues().add("Band & Draht");
		}
	}

	public static class MengeProperty extends DoublePropertyDefinition {
		MengeProperty() {
		
			this.setId("menge");
			this.setName("Menge");
			this.setUnit("t");
		}
	}

	public static class MinimaleMengeProperty extends DoublePropertyDefinition {
		MinimaleMengeProperty() {
		
			this.setId("minimale_menge");
			this.setName("minimale Menge");
			this.setUnit("t");
		}
	}

	public static class LieferdatumProperty extends DatePropertyDefinition {
		LieferdatumProperty() {
		
			this.setId("lieferdatum");
			this.setName("Lieferdatum (spätestens)");
		}
	}

	public static class WerkstoffBereitgestelltProperty extends TextPropertyDefinition {
		WerkstoffBereitgestelltProperty() {
		
			this.setId("werkstoff_bereitgestellt");
			this.setName("Werkstoff bereitgestellt");
			this.setAllowedValues(new ArrayList<>());
			this.getAllowedValues().add("Ja");
			this.getAllowedValues().add("Nein");
		}
	}

	public static class BeschreibungZusatzinfoProperty extends LongTextPropertyDefinition {
		BeschreibungZusatzinfoProperty() {
		
			this.setId("allgemeine_beschreibung");
			this.setName("allgemeine Beschreibung / Zusatzinformationen");
		}
	}

	public static class DurchmesserInnenProperty extends LongPropertyDefinition {
		public DurchmesserInnenProperty() {
		
			this.setId("durchmesser_innen");
			this.setName("Durchmesser (innen)");
			this.setUnit("mm");
		}
	}

	public static class DurchmesserAussenProperty extends LongPropertyDefinition {
		public DurchmesserAussenProperty() {
		
			this.setId("durchmesser_aussen");
			this.setName("Durchmesser (außen)");
			this.setUnit("mm");
		}
	}

	public static class HoeheProperty extends LongPropertyDefinition {
		public HoeheProperty() {
		
			this.setId("hoehe");
			this.setName("Höhe");
			this.setUnit("mm");
		}
	}

	public static class WerkstoffProperty extends TextPropertyDefinition {
		public WerkstoffProperty() {
		
			this.setId("werkstoff");
			this.setName("Werkstoff");
			this.setAllowedValues(new ArrayList<>());
			this.getAllowedValues().add("Eintrag 1");
			this.getAllowedValues().add("Eintrag 2");
			this.getAllowedValues().add("Eintrag 3");
			this.getAllowedValues().add("...");
			this.getAllowedValues().add("Freitext");
		}
	}

	public static class DurchmesserProperty extends LongPropertyDefinition {
		public DurchmesserProperty() {
		
			this.setId("durchmesser");
			this.setName("Durchmesser");
			this.setUnit("mm");
		}
	}

	public static class WerkstoffFreitextProperty extends LongTextPropertyDefinition {
		public WerkstoffFreitextProperty() {
		
			this.setId("werkstoff_freitext");
			this.setName("Werkstoff (Freitext)");
		}
	}

	public static class ZugfestigkeitProperty extends LongPropertyDefinition {
		public ZugfestigkeitProperty() {
		
			this.setId("zugfestigkeit");
			this.setName("Zugfestigkeit");
			this.setUnit("N/mm²");
		}
	}

	public static class SchutzgasProperty extends TextPropertyDefinition {
		public SchutzgasProperty() {
		
			this.setId("schutzgas");
			this.setName("Schutzgas");
			this.setAllowedValues(new ArrayList<>());
			this.getAllowedValues().add("H2 0%, N2 100%");
			this.getAllowedValues().add("H2 10%, N2 90%");
			this.getAllowedValues().add("H2 20%, N2 80%");
			this.getAllowedValues().add("H2 30%, N2 70%");
			this.getAllowedValues().add("H2 40%, N2 60%");
			this.getAllowedValues().add("H2 50%, N2 50%");
			this.getAllowedValues().add("H2 60%, N2 40%");
			this.getAllowedValues().add("H2 70%, N2 30%");
			this.getAllowedValues().add("H2 80%, N2 20%");
			this.getAllowedValues().add("H2 90%, N2 10%");
			this.getAllowedValues().add("H2 100%, N2 0%");
		}
	}

	public static class GluehreiseProperty extends TextPropertyDefinition {
		public GluehreiseProperty() {
		
			this.setId("gluehreise");
			this.setName("Glühprogramm / -reise");
		}
	}

	public static class ErforderlicheTemperaturhomogenitaetProperty extends LongPropertyDefinition {
		public ErforderlicheTemperaturhomogenitaetProperty() {
		
			this.setId("erforderliche_temperaturhomogenitaet");
			this.setName("erforderliche Temperaturhomogenität");
			this.setUnit("°C (+/-)");
		}
	}

	public static class OberflaechenqualitaetProperty extends TextPropertyDefinition {
		public OberflaechenqualitaetProperty() {
		
			this.setId("oberflaechenqualitaet");
			this.setName("Oberflächenqualität");
			this.setAllowedValues(new ArrayList<>());
			this.getAllowedValues().add("blank");
			this.getAllowedValues().add("schwarz");
		}
	}

	public static class ZusaetzlicheProduktinformationenProperty extends LongTextPropertyDefinition {
		public ZusaetzlicheProduktinformationenProperty() {
		
			this.setId("zusaetzliche_produktinformationen");
			this.setName("Zusätzliche Produkt- und Bearbeitungsinformationen");
		}
	}

	public static class IncotermsProperty extends TextPropertyDefinition {
		public IncotermsProperty() {
		
			this.setId("incoterms");
			this.setName("Incoterms");
			this.setAllowedValues(new ArrayList<>());
			this.getAllowedValues().add("EXW");
			this.getAllowedValues().add("DAP");
		}
	}

	public static class LieferortProperty extends TextPropertyDefinition {
		public LieferortProperty() {
		
			this.setId("lieferort");
			this.setName("Lieferort");
		}
	}

	public static class AbholortProperty extends TextPropertyDefinition {
		public AbholortProperty() {
		
			this.setId("abholort");
			this.setName("Abholort");
		}
	}

	public static class VerpackungsvorgabenProperty extends LongTextPropertyDefinition {
		public VerpackungsvorgabenProperty() {
		
			this.setId("verpackungsvorgaben");
			this.setName("Verpackungsvorgaben");
		}
	}

	public static class BandDickeProperty extends LongPropertyDefinition {
		public BandDickeProperty() {
		
			this.setId("banddicke");
			this.setName("Banddicke");
			this.setUnit("mm");
		}
	}

	public static class BandBreiteProperty extends LongPropertyDefinition {
		public BandBreiteProperty() {
		
			this.setId("bandbreite");
			this.setName("Bandbreite");
			this.setUnit("mm");
			;
		}
	}

	public static class DurchmesserKronenstockProperty extends LongPropertyDefinition {
		public DurchmesserKronenstockProperty() {
		
			this.setId("durchmesser_kronenstock");
			this.setName("Durchmesser Kronenstock");
			this.setUnit("mm");
			;
		}
	}

	public static class MaximaldurchmesserBundProperty extends LongPropertyDefinition {
		public MaximaldurchmesserBundProperty() {
		
			this.setId("maximaldurchmesser_bund");
			this.setName("Maximaldurchmesser Bund");
			this.setUnit("mm");
			;
		}
	}

	public static class DurchmesserDornProperty extends LongPropertyDefinition {
		public DurchmesserDornProperty() {
		
			this.setId("durchmesser_dorn");
			this.setName("Durchmesser Dorn");
			this.setUnit("mm");
			;
		}
	}

	public static class InnendurchmesserOfenProperty extends LongPropertyDefinition {
		public InnendurchmesserOfenProperty() {
		
			this.setId("innendurchmesser_ofen");
			this.setName("Innendurchmesser Ofen");
			this.setUnit("mm");
			;
		}
	}

	public static class OfenHoeheProperty extends LongPropertyDefinition {
		public OfenHoeheProperty() {
		
			this.setId("ofenhoehe");
			this.setName("Ofenhöhe");
			this.setUnit("mm");
			;
		}
	}

	public static class MaxGluehtemperaturProperty extends LongPropertyDefinition {
		public MaxGluehtemperaturProperty() {
		
			this.setId("max_gluehtemperatur");
			this.setName("Max. Glühtemperatur");
			this.setUnit("°C");
			;
		}
	}

	public static class TemperaturhomogenitaetProperty extends LongPropertyDefinition {
		public TemperaturhomogenitaetProperty() {
		
			this.setId("temperaturhomogenitaet");
			this.setName("Temperaturhomogenität");
			this.setUnit("°C");
			;
		}
	}

	public static class AufheizrateProperty extends DoublePropertyDefinition {
		public AufheizrateProperty() {
		
			this.setId("aufheizrate");
			this.setName("Aufheizrate");
		}
	}

	public static class AbkuehlrateProperty extends DoublePropertyDefinition {
		public AbkuehlrateProperty() {
		
			this.setId("abkuehlrate");
			this.setName("Abkühlrate");
		}
	}

	public static class GluehprogrammVerfuegbarProperty extends BooleanPropertyDefinition {
		public GluehprogrammVerfuegbarProperty() {
		
			this.setId("gluehprogramm_verfuegbar");
			this.setName("Glühprogramm /-reise verfügbar?");
		}
	}

	public static class MaxAnteilH2Property extends LongPropertyDefinition {
		public MaxAnteilH2Property() {
		
			this.setId("max_anteil_h2");
			this.setName("Maximaler Anteil H2");
			this.setAllowedValues(new ArrayList<>());
			this.getAllowedValues().add(10L);
			this.getAllowedValues().add(20L);
			this.getAllowedValues().add(30L);
			this.getAllowedValues().add(40L);
			this.getAllowedValues().add(50L);
			this.getAllowedValues().add(60L);
			this.getAllowedValues().add(70L);
			this.getAllowedValues().add(80L);
			this.getAllowedValues().add(90L);
			this.getAllowedValues().add(100L);
			this.setUnit("%");
		}
	}

	public static class KapazitaetProperty extends TextPropertyDefinition {
		public KapazitaetProperty() {
		
			this.setId("kapazitaet");
			this.setName("Kapazität");
		}
	}

}